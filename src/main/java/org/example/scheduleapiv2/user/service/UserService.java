package org.example.scheduleapiv2.user.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.user.dto.UserCreateRequest;
import org.example.scheduleapiv2.user.dto.UserResponse;
import org.example.scheduleapiv2.user.entity.User;
import org.example.scheduleapiv2.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
