package org.example.scheduleapiv2.comment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scheduleapiv2.common.entity.BaseEntity;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.user.entity.User;

/**
 * 일정(Schedule)에 대한 댓글(Comment) 엔티티 클래스.
 *
 * 댓글 내용, 작성자(User), 일정(Schedule) 정보를 포함하며,
 * 생성/수정 시각은 {@link BaseEntity}를 통해 자동 관리됩니다.
 */
@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    /** 댓글 ID (PK) */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 댓글 내용 */
    private String contents;

    /** 댓글 작성자 (User 엔티티와 다대일 연관 관계) */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** 댓글이 속한 일정 (Schedule 엔티티와 다대일 연관 관계) */
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;


    @Builder
    private Comment(String contents, User user, Schedule schedule) {
        this.contents = contents;
        this.user = user;
        this.schedule = schedule;
    }

    /**
     * 댓글 엔티티를 생성하는 정적 팩토리 메서드.
     *
     * @param contents 댓글 내용
     * @param user 작성자
     * @param schedule 댓글이 속한 일정
     * @return 생성된 Comment 인스턴스
     */
    public static Comment create(String contents, User user, Schedule schedule) {
        return Comment.builder()
                .contents(contents)
                .user(user)
                .schedule(schedule)
                .build();
    }

    /**
     * 댓글 내용을 수정합니다.
     *
     * @param contents 수정할 댓글 내용
     */
    public void updateContents(String contents) {
        this.contents = contents;
    }
}
