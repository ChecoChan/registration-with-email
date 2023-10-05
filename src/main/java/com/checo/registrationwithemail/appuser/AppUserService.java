package com.checo.registrationwithemail.appuser;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface AppUserService extends IService<AppUser>, UserDetailsService {
    String signUpUser(AppUser appUser);

    Optional<AppUser> findByEmail(String email);

    void enableAppUser(Long appUserId);
}
