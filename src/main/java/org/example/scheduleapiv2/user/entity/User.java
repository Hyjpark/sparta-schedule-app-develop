package org.example.scheduleapiv2.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scheduleapiv2.common.entity.BaseEntity;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;

    @Builder
    private User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User create(String name, String email, String password) {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    public void updateNameAndEmail(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
