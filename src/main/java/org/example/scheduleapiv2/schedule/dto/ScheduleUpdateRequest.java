package org.example.scheduleapiv2.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleUpdateRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 10, message = "제목은 10자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String contents;
}
