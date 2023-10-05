package com.checo.registrationwithemail.registration;

import com.baomidou.mybatisplus.extension.service.IService;

public interface RegistrationService {
    String register(RegistrationRequest request);
    String confirmToken(String token);
}
