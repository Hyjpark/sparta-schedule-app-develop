package org.example.scheduleapiv2.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 일정 생성 요청 데이터를 담는 DTO 클래스.
 *
 * 클라이언트에서 전달한 일정 제목과 내용을 검증합니다.
 * 제목은 필수이며 최대 10자까지 입력 가능(@NotBlank, @Size).
 * 내용은 필수(@NotBlank)입니다.
 */
@Getter
public class ScheduleCreateRequest {

    /** 일정 제목 (필수, 최대 10자) */
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 10, message = "제목은 10자 이내로 입력해주세요.")
    private String title;

    /** 일정 내용 (필수) */
    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;
}
