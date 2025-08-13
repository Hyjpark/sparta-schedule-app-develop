package org.example.scheduleapiv2.comment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scheduleapiv2.common.entity.BaseEntity;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.user.entity.User;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Builder
    private Comment(String contents, User user, Schedule schedule) {
        this.contents = contents;
        this.user = user;
        this.schedule = schedule;
    }

    public static Comment create(String contents, User user, Schedule schedule) {
        return Comment.builder()
                .contents(contents)
                .user(user)
                .schedule(schedule)
                .build();
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
}
