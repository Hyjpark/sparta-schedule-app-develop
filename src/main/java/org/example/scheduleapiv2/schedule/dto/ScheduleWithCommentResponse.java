package org.example.scheduleapiv2.schedule.dto;

import lombok.Getter;
import org.example.scheduleapiv2.comment.dto.CommentPagingResponse;
import org.example.scheduleapiv2.comment.dto.CommentResponse;
import org.example.scheduleapiv2.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleWithCommentResponse {

    private final Long id;
    private final String title;
    private final String contents;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<CommentPagingResponse> comments;


    private ScheduleWithCommentResponse(Long id, String title, String contents, Long userId, LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentPagingResponse> comments) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userId = userId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }

    public static ScheduleWithCommentResponse of(Schedule schedule, List<CommentPagingResponse> commentPage) {
        return new ScheduleWithCommentResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getId(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                commentPage
        );
    }
}
