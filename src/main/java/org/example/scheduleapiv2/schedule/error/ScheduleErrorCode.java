package org.example.scheduleapiv2.schedule.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.error.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode implements ErrorCode {
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 일정을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
