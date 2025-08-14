package org.example.scheduleapiv2.schedule.dto;

import lombok.Getter;
import org.example.scheduleapiv2.schedule.entity.Schedule;

import java.time.LocalDateTime;

/**
 * 일정 조회(Paging) 응답 데이터를 담는 DTO 클래스.
 */
@Getter
public class SchedulePagingResponse {

    /** 일정 ID */
    private final Long id;

    /** 일정 제목 */
    private final String title;

    /** 일정 내용 */
    private final String contents;

    /** 일정에 등록된 댓글 개수 */
    private final int commentCount;

    /** 일정 생성 시각 */
    private final LocalDateTime createdAt;

    /** 일정 생성 시각 */
    private final LocalDateTime modifiedAt;

    /** 일정 작성자명 */
    private final String name;


    private SchedulePagingResponse(Long id, String title, String contents, int commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String name) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.name = name;
    }

    /**
     * {@link Schedule} 엔티티와 댓글 개수, 작성자명을 기반으로 SchedulePagingResponse 생성합니다.
     *
     * @param schedule 일정 엔티티
     * @param commentCount 댓글 개수
     * @param username 작성자명
     * @return 생성된 SchedulePagingResponse 인스턴스
     */
    public static SchedulePagingResponse of(Schedule schedule, int commentCount, String username) {
        return new SchedulePagingResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                commentCount,
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                username
        );
    }
}
