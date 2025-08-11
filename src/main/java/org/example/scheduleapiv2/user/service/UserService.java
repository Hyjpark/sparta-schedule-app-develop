package org.example.scheduleapiv2.user.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.user.dto.UserCreateRequest;
import org.example.scheduleapiv2.user.dto.UserResponse;
import org.example.scheduleapiv2.user.dto.UserUpdateRequest;
import org.example.scheduleapiv2.user.entity.User;
import org.example.scheduleapiv2.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        User user = new User(request.getName(), request.getEmail());

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
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findByIdOrElseThrow(userId);
        user.updateNameAndEmail(request.getName(), request.getEmail());

        return UserResponse.of(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);
        userRepository.delete(user);
    }
}
