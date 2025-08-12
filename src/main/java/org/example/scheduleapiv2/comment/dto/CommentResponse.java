package org.example.scheduleapiv2.comment.dto;

import lombok.Getter;
import org.example.scheduleapiv2.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long id;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Long userId;
    private final Long scheduleId;

    public CommentResponse(Long id, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, Long userId, Long scheduleId) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userId = userId;
        this.scheduleId = scheduleId;
    }

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                comment.getUser().getId(),
                comment.getSchedule().getId()
        );
    }
}
