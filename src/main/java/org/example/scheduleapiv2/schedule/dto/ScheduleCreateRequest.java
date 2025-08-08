package org.example.scheduleapiv2.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleCreateRequest {
    private String title;
    private String contents;
    private String author;
}
