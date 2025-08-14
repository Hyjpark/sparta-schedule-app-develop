package org.example.scheduleapiv2.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 모든 엔티티에서 공통적으로 사용하는
 * 생성 시간(createdAt)과 수정 시간(modifiedAt)을 관리하는 추상 클래스입니다.
 *
 * Spring Data JPA의 Auditing 기능을 사용하여 엔티티의 생성 및 수정 시각을
 * 자동으로 기록합니다.
 *
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /**
     * 엔티티가 생성된 시간
     * 엔티티가 최초로 저장될 때 자동으로 설정됩니다.
     */
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    /**
     * 엔티티가 마지막으로 수정된 시간.
     * 엔티티가 변경될 때마다 자동으로 갱신됩니다.
     */
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}
