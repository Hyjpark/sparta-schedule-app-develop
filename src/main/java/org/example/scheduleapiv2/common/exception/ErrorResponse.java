package org.example.scheduleapiv2.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * API 요청 실패 시 클라이언트에 반환되는 표준 에러 응답 객체.
 *
 * 에러 코드({@code code}), 에러 메시지({@code message}),
 * 입력 값 검증 실패 시 발생하는 상세 필드 오류 목록({@code errors})을 포함합니다.
 */
@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    /** 에러 코드(예: INVALID_PARAMETER, RESOURCE_NOT_FOUND) */
    private final String code;

    /** 에러 메시지 */
    private final String message;

    /** 필드 유효성 검증 실패 목록 (없을 경우 비어 있음) */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    /**
     * 입력 값 검증 실패 시, 오류 필드와 메시지를 담는 내부 정적 클래스
     */
    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {

        /** 오류가 발생한 필드명 */
        private final String field;

        /** 해당 필드에 대한 오류 메시지 */
        private final String message;

        /**
         * {@link FieldError} 객체를 기반으로 ValidationError 객체를 생성합니다.
         * 스프링 검증 오류 정보를 API 응답 형식에 맞게 변환합니다.
         *
         * @param fieldError 스프링의 {@link FieldError} 객체
         * @return 변환 결과인 {@code ValidationError} 인스턴스
         */
        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
