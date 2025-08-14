package org.example.scheduleapiv2.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.example.scheduleapiv2.user.dto.*;
import org.example.scheduleapiv2.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자(User) 관련 요청을 처리하는 컨트롤러 클래스.
 *
 * 회원가입, 로그인, 조회, 수정, 삭제 기능을 제공하며,
 * 로그인 세션 및 사용자 권한 검증을 포함합니다.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자를 생성합니다.
     *
     * @param request 사용자 생성 요청 데이터
     * @return 생성된 사용자 정보와 HTTP 상태 201(CREATED)
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    /**
     * 사용자를 로그인 처리하고 세션을 생성합니다.
     *
     * @param request 로그인 요청 데이터(이메일, 비밀번호)
     * @param httpServletRequest HttpServletRequest 객체
     * @return 로그인 성공 시 HTTP 상태 200(OK)
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserLoginRequest request, HttpServletRequest httpServletRequest) {
        UserLoginResponse user = userService.login(request.getEmail(), request.getPassword());

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("LOGIN_USER", user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 모든 사용자 정보를 조회합니다.
     *
     * @return 사용자 목록과 HTTP 상태 200(OK)
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    /**
     * 특정 사용자 정보를 조회합니다.
     *
     * @param userId 조회할 사용자 ID
     * @return 조회된 사용자 정보와 HTTP 상태 200(OK)
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    /**
     * 사용자 정보를 수정합니다.
     *
     * @param userId 수정할 사용자 ID
     * @param request 수정할 사용자 데이터
     * @param httpRequest HttpServletRequest 객체 (세션 조회용)
     * @return 수정된 사용자 정보와 HTTP 상태 200(OK)
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequest request,
            HttpServletRequest httpRequest
    ) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);
        return new ResponseEntity<>(userService.updateUser(sessionUserId, userId, request), HttpStatus.OK);
    }

    /**
     * 사용자를 삭제합니다.
     *
     * @param userId 삭제할 사용자 ID
     * @param httpRequest HttpServletRequest 객체 (세션 조회용)
     * @return HTTP 상태 200(OK)
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, HttpServletRequest httpRequest) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);
        userService.deleteUser(sessionUserId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
