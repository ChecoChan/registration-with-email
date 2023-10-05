package com.checo.registrationwithemail.registration.token;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Optional;

public interface ConfirmationService extends IService<ConfirmationToken> {
    Optional<ConfirmationToken> findByToken(String token);
    void saveConfirmationToken(ConfirmationToken token);
    void setConfirmTime(String token);
}
