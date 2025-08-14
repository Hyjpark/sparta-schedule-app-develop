package org.example.scheduleapiv2.schedule.dto;

import lombok.Getter;
import org.example.scheduleapiv2.comment.dto.CommentPagingResponse;
import org.example.scheduleapiv2.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 댓글을 포함한 일정 조회 응답 데이터를 담는 DTO 클래스.
 *
 * 단일 일정 정보와 해당 일정에 달린 댓글 목록을 함께 전달합니다.
 * 댓글은 {@link CommentPagingResponse} 리스트로 포함됩니다.
 */
@Getter
public class ScheduleWithCommentResponse {

    /** 일정 ID */
    private final Long id;

    /** 일정 제목 */
    private final String title;

    /** 일정 내용 */
    private final String contents;

    /** 작성자 ID */
    private final Long userId;

    /** 생성 일시 */
    private final LocalDateTime createdAt;

    /** 수정 일시 */
    private final LocalDateTime modifiedAt;

    /** 일정에 달린 댓글 목록 */
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

    /**
     * {@link Schedule} 엔티티와 댓글 목록을 기반으로 ScheduleWithCommentResponse 생성합니다.
     *
     * @param schedule 일정 엔티티
     * @param commentPage 댓글 목록
     * @return 생성된 ScheduleWithCommentResponse 인스턴스
     */
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
