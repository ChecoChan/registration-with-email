package com.checo.registrationwithemail.registration;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.checo.registrationwithemail.appuser.AppUser;
import com.checo.registrationwithemail.appuser.AppUserRole;
import com.checo.registrationwithemail.appuser.AppUserService;
import com.checo.registrationwithemail.email.EmailSender;
import com.checo.registrationwithemail.email.EmailUtils;
import com.checo.registrationwithemail.registration.token.ConfirmationService;
import com.checo.registrationwithemail.registration.token.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private ConfirmationService confirmationService;

    @Autowired
    private EmailSender emailSender;

    @Value("${registration.confirm-link}")
    String linkWithoutToken;

    @Override
    public String register(RegistrationRequest request) {
        boolean isValidaEmail = emailValidator.test(request.getEmail());
        if (!isValidaEmail)
            throw new IllegalStateException("email not valid");
        String token = appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
        String confirmationLink =  linkWithoutToken + token;
        emailSender.send(request.getEmail(), EmailUtils.buildEmail(request.getFirstName(), confirmationLink));
        return token;
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationService
                .findByToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmTime() != null)
            throw new IllegalStateException("email already confirmed");

        LocalDateTime expiredTime = confirmationToken.getExpiredTime();
        if (expiredTime.isBefore(LocalDateTime.now()))
            throw new IllegalStateException("token expired");

        confirmationService.setConfirmTime(token);
        appUserService.enableAppUser(confirmationToken.getAppUserId());
        return "confirmed";
    }
}
