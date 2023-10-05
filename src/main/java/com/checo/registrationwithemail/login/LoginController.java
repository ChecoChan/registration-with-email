package com.checo.registrationwithemail.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login")
public class LoginController {

    @GetMapping("/success")
    public String loginSuccess() {
        return "login success";
    }
}
