package org.example.scheduleapiv2.schedule.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.error.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * 일정(Schedule) 관련 전역 에러 코드를 정의한 Enum 클래스.
 *
 * 각 코드에는 HTTP 상태 코드와 사용자에게 전달할 메시지를 포함합니다.
 * {@link ErrorCode} 인터페이스를 구현합니다.
 */
@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode implements ErrorCode {

    /** 요청한 일정을 찾을 수 없는 경우 */
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 일정을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
