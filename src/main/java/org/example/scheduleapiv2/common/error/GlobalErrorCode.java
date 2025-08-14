package org.example.scheduleapiv2.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 전역에서 사용하는 표준 에러 코드 집합.
 *
 * 각 상수는 {@link HttpStatus}와 에러 메시지를 함께 가지고 있으며,
 * {@link ErrorCode} 인터페이스를 구현하여 일관된 에러 응답을 제공한다.
 */
@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    /** 요청 파라미터가 유효하지 않음 */
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "요청이 올바르지 않습니다."),

    /** 요청 리소스를 찾을 수 없음 */
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),

    /** 요청 리소스에 접근 권한이 없음 */
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "요청한 리소스에 대한 접근 권한이 없습니다."),

    /** 유효한 세션이 존재하지 않음 */
    SESSION_NOT_FOUND(HttpStatus.UNAUTHORIZED, "유효한 세션이 없습니다."),

    /** 필요한 세션 속성이 존재하지 않음 */
    SESSION_ATTRIBUTE_NOT_FOUND(HttpStatus.UNAUTHORIZED, "필요한 세션 속성을 찾을 수 없습니다."),

    /** 로그인이 필요함 */
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
