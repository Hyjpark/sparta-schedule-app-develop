package org.example.scheduleapiv2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 사용자 수정 요청 데이터를 담는 DTO 클래스.
 *
 * 클라이언트에서 전달한 이름, 이메일을 검증(@NotBlank, @Size, @Email)합니다.
 */
@Getter
public class UserUpdateRequest {
    /** 사용자 이름 (2~18자) */
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 18, message = "이름은 2자 이상 18자 이하로 입력해주세요.")
    private String name;

    /** 사용자 이메일 주소 (유효한 이메일 형식) */
    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email
    private String email;
}
