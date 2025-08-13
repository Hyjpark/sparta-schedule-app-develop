package org.example.scheduleapiv2.user.dto;

import lombok.Getter;

@Getter
public class UserLoginResponse {
    private Long id;

    private UserLoginResponse(Long id) {
        this.id = id;
    }

    public static UserLoginResponse of(Long id) {
        return new UserLoginResponse(id);
    }
}
