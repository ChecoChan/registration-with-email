package com.checo.registrationwithemail.appuser;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.checo.registrationwithemail.email.EmailService;
import com.checo.registrationwithemail.email.EmailUtils;
import com.checo.registrationwithemail.registration.token.ConfirmationService;
import com.checo.registrationwithemail.registration.token.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {
    private static final String USER_NOT_FOUND = "User with email %s not found";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ConfirmationService confirmationService;

    @Autowired
    private EmailService emailService;

    @Value("${registration.confirm-link}")
    String linkWithoutToken;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    @Override
    public String signUpUser(AppUser appUser) {
        Optional<AppUser> optionalAppUser = findByEmail(appUser.getEmail());
        boolean userExists = optionalAppUser.isPresent();
        if (userExists) {
            AppUser user = optionalAppUser.get();
            if (appUser.getEmail().equals(user.getEmail()) && !user.isEnabled()) {
                this.updateById(appUser);
                LambdaQueryWrapper<ConfirmationToken> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ConfirmationToken::getAppUserId, user.getId());
                ConfirmationToken confirmationToken = confirmationService.getOne(queryWrapper);
                String token = confirmationToken.getToken();
                String confirmationLink =  linkWithoutToken + token;
                emailService.send(appUser.getEmail(), EmailUtils.buildEmail(appUser.getFirstName(), confirmationLink));
                throw new IllegalStateException("email already taken and confirmation email resend");
            }
            throw new IllegalStateException("email already taken");
        }
        String encodePassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodePassword);
        this.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                findByEmail(appUser.getEmail()).get().getId()
        );
        confirmationService.saveConfirmationToken(confirmationToken);
        return token;
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        LambdaQueryWrapper<AppUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppUser::getEmail, email);
        AppUser appUser = this.getOne(queryWrapper);
        return Optional.ofNullable(appUser);
    }

    @Override
    public void enableAppUser(Long appUserId) {
        AppUser appUser = this.getById(appUserId);
        appUser.setEnabled(true);
        this.updateById(appUser);
    }
}
