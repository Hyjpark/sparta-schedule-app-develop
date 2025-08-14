package org.example.scheduleapiv2.user.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.error.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 사용자(User) 관련 전역 에러 코드를 정의한 Enum 클래스.
 *
 * 각 에러 코드는 {@link ErrorCode}를 구현하며, HTTP 상태 코드와 메시지를 포함합니다.
 */
@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    /** 이미 사용 중인 이메일일 경우 */
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),

    /** 비밀번호 불일치할 경우 */
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    /** 사용자를 찾을 수 없을 경우 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
