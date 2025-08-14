package org.example.scheduleapiv2.schedule.dto;

import lombok.Getter;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 일정 응답 데이터를 담는 DTO 클래스.
 */
@Getter
public class ScheduleResponse {

    /** 일정 ID */
    private final Long id;

    /** 일정 제목 */
    private final String title;

    /** 일정 내용 */
    private final String contents;

    /** 일정 작성자 ID */
    private final Long userId;

    /** 일정 생성 시각 */
    private final LocalDateTime createdAt;

    /** 일정 수정 시각 */
    private final LocalDateTime modifiedAt;

    private ScheduleResponse(Long id, String title, String contents, User user, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userId = user.getId();
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * {@link Schedule} 엔티티를 기반으로 ScheduleResponse 생성합니다.
     * 
     * @param schedule 일정 엔티티
     * @return 생성된 ScheduleResponse 인스턴스
     */
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
