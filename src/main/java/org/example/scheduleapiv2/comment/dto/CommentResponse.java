package org.example.scheduleapiv2.comment.dto;

import lombok.Getter;
import org.example.scheduleapiv2.comment.entity.Comment;

import java.time.LocalDateTime;

/**
 * 댓글 응답 데이터를 담는 DTO 클래스.
 */
@Getter
public class CommentResponse {

    /** 댓글 ID */
    private final Long id;

    /** 댓글 내용 */
    private final String contents;

    /** 댓글 생성 시각 */
    private final LocalDateTime createdAt;

    /** 댓글 수정 시각 */
    private final LocalDateTime modifiedAt;

    /** 댓글 작성자 ID */
    private final Long userId;

    /** 댓글이 속한 일정 ID */
    private final Long scheduleId;

    private CommentResponse(Long id, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, Long userId, Long scheduleId) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userId = userId;
        this.scheduleId = scheduleId;
    }

    /**
     * {@link Comment} 엔티티를 기반으로 CommentResponse를 생성합니다.
     *
     * @param comment 댓글 엔티티
     * @return 생성된 CommentResponse 인스턴스
     */
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
