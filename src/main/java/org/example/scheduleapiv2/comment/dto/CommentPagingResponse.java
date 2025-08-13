package org.example.scheduleapiv2.comment.dto;

import lombok.Getter;
import org.example.scheduleapiv2.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentPagingResponse {
    private final String contents;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private CommentPagingResponse(String contents, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.contents = contents;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentPagingResponse of(Comment comment, String name) {
        return new CommentPagingResponse(
                comment.getContents(),
                name,
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
