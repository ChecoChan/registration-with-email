package com.checo.registrationwithemail.registration.token;

import com.baomidou.mybatisplus.annotation.TableField;
import com.checo.registrationwithemail.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConfirmationToken {
    private Long id;
    private String token;
    private LocalDateTime createTime;
    private LocalDateTime expiredTime;
    private LocalDateTime confirmTime;
    private Long appUserId;

    public ConfirmationToken(String token, LocalDateTime createTime, LocalDateTime expiredTime, Long appUserId) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTime = expiredTime;
        this.appUserId = appUserId;
    }
}
