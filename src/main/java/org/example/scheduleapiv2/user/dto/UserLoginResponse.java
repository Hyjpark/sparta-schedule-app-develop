package org.example.scheduleapiv2.user.dto;

import lombok.Getter;

@Getter
public class UserLoginResponse {
    private Long id;

    public UserLoginResponse(Long id) {
        this.id = id;
    }
}
