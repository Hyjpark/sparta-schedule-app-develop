package org.example.scheduleapiv2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 18, message = "이름은 2자 이상 18자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email
    private String email;
}
