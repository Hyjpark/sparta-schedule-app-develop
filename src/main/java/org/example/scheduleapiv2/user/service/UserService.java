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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        checkEmailDuplication(request.getEmail());

        String encodePw = passwordEncoder.encode(request.getPassword());

        User user = User.create(request.getName(), request.getEmail(), encodePw);

        User savedUser = userRepository.save(user);

        return UserResponse.of(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findUserById(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        return UserResponse.of(user);
    }

    @Transactional
    public UserResponse updateUser(Long sessionUserId, Long userId, UserUpdateRequest request) {
        User user = userRepository.findByIdOrElseThrow(userId);

        SessionUtils.assertUserIsOwner(sessionUserId, user.getId());

        checkEmailDuplication(request.getEmail());
        user.updateNameAndEmail(request.getName(), request.getEmail());

        return UserResponse.of(user);
    }

    @Transactional
    public void deleteUser(Long sessionUserId, Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        SessionUtils.assertUserIsOwner(sessionUserId, user.getId());

        commentRepository.deleteByUserId(user.getId());

        scheduleRepository.deleteByUserId(user.getId());

        userRepository.delete(user);
    }

    public UserLoginResponse login(String email, String password) {
        User user = userRepository.findByEmailOrElseThrow(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException(UserErrorCode.PASSWORD_MISMATCH);
        }

        return new UserLoginResponse(user.getId());
    }

    private void checkEmailDuplication(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new ApiException(UserErrorCode.EMAIL_DUPLICATION);
        });
    }
}
