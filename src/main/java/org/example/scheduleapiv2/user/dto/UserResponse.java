package org.example.scheduleapiv2.user.dto;

import lombok.Getter;
import org.example.scheduleapiv2.user.entity.User;

import java.time.LocalDateTime;

/**
 * 사용자 응답 데이터를 담는 DTO 클래스.
 */
@Getter
public class UserResponse {

    /** 사용자 ID */
    private final Long id;

    /** 사용자 이름 */
    private final String name;

    /** 사용자 이메일 */
    private final String email;

    /** 사용자 생성 시각 */
    private final LocalDateTime createdAt;

    /** 사용자 수정 시각 */
    private final LocalDateTime modifiedAt;

    private UserResponse(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * {@link User} 엔티티를 기반으로 UserResponse 생성합니다.
     *
     * @param user 사용자 엔티티
     * @return 생성된 UserResponse 인스턴스
     */
    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
