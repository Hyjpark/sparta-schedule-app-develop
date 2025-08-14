package org.example.scheduleapiv2.common.error;

import org.springframework.http.HttpStatus;

/**
 * 에러 코드 표준 인터페이스
 *
 * 이 인터페이스를 구현한 Enum을 사용하면 일관된 형식으로
 * 에러 응답을 관리할 수 있습니다.
 */
public interface ErrorCode {

    /**
     * 에러 코드의 이름(상수명)을 반환합니다.
     *
     * @return 에러 코드 이름
     */
    String name();

    /**
     * 에러에 해당하는 HTTP 상태 코드를 반환합니다.
     *
     * @return {@link HttpStatus} 값
     */
    HttpStatus getHttpStatus();

    /**
     * 에러에 대한 설명 메시지를 반환합니다.
     *
     * @return 에러 메시지
     */
    String getMessage();
}
