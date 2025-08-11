package org.example.scheduleapiv2.user.dto;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String email;
    private String password;
}
