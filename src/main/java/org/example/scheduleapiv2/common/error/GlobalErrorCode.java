package org.example.scheduleapiv2.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청이 올바르지 않습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "요청한 리소스에 대한 접근 권한이 없습니다."),
    SESSION_NOT_FOUND(HttpStatus.UNAUTHORIZED, "유효한 세션이 없습니다."),
    SESSION_ATTRIBUTE_NOT_FOUND(HttpStatus.UNAUTHORIZED, "필요한 세션 속성을 찾을 수 없습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
