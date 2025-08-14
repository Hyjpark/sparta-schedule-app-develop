package org.example.scheduleapiv2.user.dto;

import lombok.Getter;

/**
 * 사용자 로그인 응답 데이터를 담는 DTO 클래스.
 *
 * 로그인 성공 시 사용자 ID를 반환합니다.
 */
@Getter
public class UserLoginResponse {

    /** 로그인한 사용자 ID */
    private Long id;

    private UserLoginResponse(Long id) {
        this.id = id;
    }

    /**
     * 로그인한 사용자 ID를 기반으로 UserLoginResponse 인스턴스를 생성합니다.
     *
     * @param id 로그인한 사용자 ID
     * @return UserLoginResponse 인스턴스
     */
    public static UserLoginResponse of(Long id) {
        return new UserLoginResponse(id);
    }
}
