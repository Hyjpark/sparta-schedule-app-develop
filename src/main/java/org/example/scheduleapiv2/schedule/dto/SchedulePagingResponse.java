package org.example.scheduleapiv2.schedule.dto;

import lombok.Getter;
import org.example.scheduleapiv2.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class SchedulePagingResponse {

    private final String title;
    private final String contents;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String name;


    public SchedulePagingResponse(String title, String contents, int commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String name) {
        this.title = title;
        this.contents = contents;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.name = name;
    }

    public static SchedulePagingResponse of(Schedule schedule, int commentCount, String username) {
        return new SchedulePagingResponse(
                schedule.getTitle(),
                schedule.getContents(),
                commentCount,
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                username
        );
    }
}
