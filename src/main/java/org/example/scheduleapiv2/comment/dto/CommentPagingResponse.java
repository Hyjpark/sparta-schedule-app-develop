package org.example.scheduleapiv2.comment.dto;

import lombok.Getter;
import org.example.scheduleapiv2.comment.entity.Comment;

import java.time.LocalDateTime;

/**
 * 댓글 조회(Paging) 응답 데이터를 담는 DTO 클래스.
 */
@Getter
public class CommentPagingResponse {

    /** 댓글 내용 */
    private final String contents;

    /** 댓글 작성자 이름 */
    private final String name;

    /** 댓글 생성 시각 */
    private final LocalDateTime createdAt;

    /** 댓글 수정 시각 */
    private final LocalDateTime modifiedAt;

    private CommentPagingResponse(String contents, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.contents = contents;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * {@link Comment} 엔티티와 작성자 이름을 기반으로 CommentPagingResponse를 생성합니다.
     *
     * @param comment 댓글 엔티티
     * @param name 작성자 이름
     * @return 생성된 CommentPagingResponse 인스턴스
     */
    public static CommentPagingResponse of(Comment comment, String name) {
        return new CommentPagingResponse(
                comment.getContents(),
                name,
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
