package org.example.scheduleapiv2.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scheduleapiv2.common.entity.BaseEntity;

/**
 * 사용자(User) 엔티티 클래스.
 *
 * 사용자 정보(이름, 이메일, 비밀번호)를 포함하며,
 * 생성/수정 시각은 {@link BaseEntity}를 통해 자동 관리됩니다.
 */
@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    /** 사용자 ID (PK) */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 사용자 이름 */
    private String name;

    /** 사용자 이메일 (유일) */
    @Column(unique = true)
    private String email;

    /** 암호화된 비밀번호 */
    private String password;

    @Builder
    private User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * 사용자 생성 정적 팩토리 메서드.
     *
     * @param name 사용자 이름
     * @param email 사용자 이메일
     * @param password 암호화된 비밀번호
     * @return 생성된 User 엔티티
     */
    public static User create(String name, String email, String password) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    /**
     * 사용자 이름과 이메일을 업데이트합니다.
     *
     * @param name 변경할 이름
     * @param email 변경할 이메일
     */
    public void updateNameAndEmail(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
