package org.example.scheduleapiv2.common.exception;

import org.example.scheduleapiv2.common.error.ErrorCode;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 전역 예외 처리 클래스.
 *
 * 컨트롤러에서 발생하는 예외를 한 곳에서 처리하여
 * 표준화된 {@link ErrorResponse} 형태로 클라이언트에 반환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * {@link ApiException} 처리
     *
     * @param e ApiException 인스턴스
     * @return ErrorCode 기반 표준 에러 응답
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    /**
     * {@link MethodArgumentNotValidException} 처리
     *
     * 입력값 검증 실패 시 발생하며,
     * {@link ErrorResponse.ValidationError} 리스트를 포함한 응답을 반환합니다.
     *
     * @param e MethodArgumentNotValidException 인스턴스
     * @return 입력값 검증 오류를 포함한 표준 에러 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = GlobalErrorCode.INVALID_PARAMETER;

        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(validationErrorList)
                .build();

        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    /**
     * 공통 예외 처리 내부 메서드
     *
     * @param errorCode 처리할 {@link ErrorCode}
     * @return 표준 에러 응답
     */
    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(errorCode));
    }

    /**
     * {@link ErrorCode}를 기반으로 {@link ErrorResponse} 생성
     *
     * @param errorCode 처리할 {@link ErrorCode}
     * @return ErrorResponse 객체
     */
    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
}
