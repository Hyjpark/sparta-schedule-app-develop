package org.example.scheduleapiv2.user.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.comment.repository.CommentRepository;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.example.scheduleapiv2.schedule.repository.ScheduleRepository;
import org.example.scheduleapiv2.security.config.PasswordEncoder;
import org.example.scheduleapiv2.user.dto.UserCreateRequest;
import org.example.scheduleapiv2.user.dto.UserLoginResponse;
import org.example.scheduleapiv2.user.dto.UserResponse;
import org.example.scheduleapiv2.user.dto.UserUpdateRequest;
import org.example.scheduleapiv2.user.entity.User;
import org.example.scheduleapiv2.user.error.UserErrorCode;
import org.example.scheduleapiv2.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 사용자(User) 관련 비즈니스 로직을 처리하는 서비스 클래스.
 *
 * 사용자 생성, 조회, 수정, 삭제, 로그인 기능을 제공하며,
 * 이메일 중복 확인 및 세션 사용자 권한 검증 등의 로직을 포함합니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 새로운 사용자를 생성합니다.
     *
     * @param request 사용자 생성 요청 데이터
     * @return 생성된 사용자 정보
     * @throws ApiException 이메일 중복일 경우
     */
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        checkEmailDuplication(request.getEmail());

        String encodePw = passwordEncoder.encode(request.getPassword());

        User user = User.create(request.getName(), request.getEmail(), encodePw);

        User savedUser = userRepository.save(user);

        return UserResponse.of(savedUser);
    }

    /**
     * 모든 사용자 목록을 조회합니다.
     *
     * @return 사용자 리스트
     */
    @Transactional(readOnly = true)
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::of)
                .toList();
    }

    /**
     * ID로 사용자를 조회합니다.
     *
     * @param userId 조회할 사용자 ID
     * @return 사용자 정보
     * @throws ApiException 사용자가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        return UserResponse.of(user);
    }

    /**
     * 사용자 정보를 수정합니다.
     *
     * @param sessionUserId 세션에 로그인한 사용자 ID
     * @param userId 수정할 사용자 ID
     * @param request 사용자 수정 요청 데이터
     * @return 수정된 사용자 정보
     * @throws ApiException 권한이 없거나 이메일 중복 시
     */
    @Transactional
    public UserResponse updateUser(Long sessionUserId, Long userId, UserUpdateRequest request) {
        User user = userRepository.findByIdOrElseThrow(userId);

        SessionUtils.assertUserIsOwner(sessionUserId, user.getId());

        checkEmailDuplication(request.getEmail());
        user.updateNameAndEmail(request.getName(), request.getEmail());

        return UserResponse.of(user);
    }

    /**
     * 사용자를 삭제합니다.
     *
     * @param sessionUserId 세션에 로그인한 사용자 ID
     * @param userId 삭제할 사용자 ID
     * @throws ApiException 권한이 없을 경우
     */
    @Transactional
    public void deleteUser(Long sessionUserId, Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        SessionUtils.assertUserIsOwner(sessionUserId, user.getId());

        commentRepository.deleteByUserId(user.getId());

        scheduleRepository.deleteByUserId(user.getId());

        userRepository.delete(user);
    }

    /**
     * 로그인 처리 후 사용자 ID를 반환합니다.
     *
     * @param email 로그인 이메일
     * @param password 로그인 비밀번호
     * @return 로그인 사용자 ID
     * @throws ApiException 이메일 또는 비밀번호 불일치 시
     */
    public UserLoginResponse login(String email, String password) {
        User user = userRepository.findByEmailOrElseThrow(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException(UserErrorCode.PASSWORD_MISMATCH);
        }

        return UserLoginResponse.of(user.getId());
    }

    /**
     * 이메일 중복 여부를 확인합니다.
     *
     * @param email 확인할 이메일
     * @throws ApiException 이미 사용 중인 이메일일 경우
     */
    private void checkEmailDuplication(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new ApiException(UserErrorCode.EMAIL_DUPLICATION);
        });
    }
}
