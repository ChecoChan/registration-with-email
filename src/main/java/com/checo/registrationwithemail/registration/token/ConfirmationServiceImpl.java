package com.checo.registrationwithemail.registration.token;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.checo.registrationwithemail.appuser.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationServiceImpl extends ServiceImpl<ConfirmationTokenMapper, ConfirmationToken> implements ConfirmationService {

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        this.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        LambdaQueryWrapper<ConfirmationToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConfirmationToken::getToken, token);
        ConfirmationToken confirmationToken = this.getOne(queryWrapper);
        return Optional.ofNullable(confirmationToken);
    }

    @Override
    public void setConfirmTime(String token) {
        ConfirmationToken confirmationToken = findByToken(token).get();
        confirmationToken.setConfirmTime(LocalDateTime.now());
        this.updateById(confirmationToken);
    }
}
