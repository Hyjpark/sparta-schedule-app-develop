package org.example.scheduleapiv2.user.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.error.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
