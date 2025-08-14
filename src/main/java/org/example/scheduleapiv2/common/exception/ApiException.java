package org.example.scheduleapiv2.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.error.ErrorCode;


/**
 * API 요청 처리 중 발생하는 비즈니스 예외를 나타내는 클래스.
 *
 * {@link ErrorCode}를 포함하여 HTTP 상태 코드와 에러 메시지를 함께 전달할 수 있도록 합니다.
 */
@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {

    /**
     * 발생한 예외에 해당하는 에러 코드.
     * HTTP 상태 코드와 에러 메시지를 포함합니다.
     */
    private final ErrorCode errorCode;
}
