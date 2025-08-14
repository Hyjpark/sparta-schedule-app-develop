package org.example.scheduleapiv2.user.repository;

import org.example.scheduleapiv2.common.exception.ApiException;
import org.example.scheduleapiv2.user.entity.User;
import org.example.scheduleapiv2.user.error.UserErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 사용자(User) 엔티티에 대한 DB 접근을 담당하는 Repository 인터페이스.
 *
 * JpaRepository를 상속받아 기본 CRUD 기능을 제공하며,
 * 이메일 조회, 존재하지 않을 경우 예외 발생 처리 등의 기능을 포함합니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일로 사용자를 조회합니다.
     *
     * @param email 조회할 이메일
     * @return 사용자 Optional
     */
    Optional<User> findByEmail(String email);

    /**
     * 이메일로 사용자 조회 후 존재하지 않으면 {@link ApiException}을 발생시킵니다.
     *
     * @param email 조회할 이메일
     * @return 사용자 엔티티
     * @throws ApiException 사용자가 존재하지 않을 경우
     */
    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() ->
                new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자 ID로 조회 후 존재하지 않으면 {@link ApiException}을 발생시킵니다.
     *
     * @param id 사용자 ID
     * @return 사용자 엔티티
     * @throws ApiException 사용자가 존재하지 않을 경우
     */
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
