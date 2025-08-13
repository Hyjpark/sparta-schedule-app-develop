package org.example.scheduleapiv2.schedule.dto;

import lombok.Getter;
import org.example.scheduleapiv2.comment.dto.CommentResponse;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.user.entity.User;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleResponse {

    private final Long id;
    private final String title;
    private final String contents;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private ScheduleResponse(Long id, String title, String contents, User user, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userId = user.getId();
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ScheduleResponse of(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getUser(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
