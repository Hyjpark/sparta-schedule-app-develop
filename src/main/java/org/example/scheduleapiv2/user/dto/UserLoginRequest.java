package org.example.scheduleapiv2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 사용자 로그인 요청 데이터를 담는 DTO 클래스.
 *
 * 클라이언트에서 전달한 이메일, 비밀번호를 검증(@NotBlank, @Size, @Email, @Pattern)합니다.
 */
@Getter
public class UserLoginRequest {
    /** 사용자 이름 (2~18자) */
    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email
    private String email;

    /** 사용자 비밀번호 (영문+숫자, 4~20자) */
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어 알파벳과 숫자만 입력할 수 있습니다.")
    private String password;
}
