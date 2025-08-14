package org.example.scheduleapiv2.schedule.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scheduleapiv2.common.entity.BaseEntity;
import org.example.scheduleapiv2.user.entity.User;

/**
 * 일정(Schedule) 엔티티 클래스.
 *
 * 제목, 내용, 작성자(User)를 정보를 포함하며,
 * 생성/수정 시각은 {@link BaseEntity}를 통해 자동 관리됩니다.
 */
@Getter
@Entity
@NoArgsConstructor
public class Schedule extends BaseEntity {

    /** 일정 ID (PK) */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 일정 제목 */
    private String title;

    /** 일정 내용 */
    private String contents;

    /** 일정 작성자 (User 엔티티와 다대일 연관 관계) */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Schedule(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    /**
     * 일정 생성 팩토리 메서드.
     *
     * @param title 일정 제목
     * @param contents 일정 내용
     * @param user 작성자
     * @return 생성된 Schedule 인스턴스
     */
    public static Schedule create(String title, String contents, User user) {
        return Schedule.builder()
                .title(title)
                .contents(contents)
                .user(user)
                .build();
    }

    /**
     * 일정 제목과 내용을 수정합니다.
     *
     * @param title 수정할 제목
     * @param contents 수정할 내용
     */
    public void updateTitleAndContents(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
