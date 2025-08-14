package org.example.scheduleapiv2.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.comment.dto.CommentCreateRequest;
import org.example.scheduleapiv2.comment.dto.CommentResponse;
import org.example.scheduleapiv2.comment.dto.CommentUpdateRequest;
import org.example.scheduleapiv2.comment.service.CommentService;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일정(Schedule)에 대한 댓글(Comment) 관련 요청을 처리하는 컨트롤러 클래스.
 *
 * 댓글 생성, 조회, 수정, 삭제 기능을 제공하며,
 * 로그인 세션 및 사용자 입력값을 검증합니다.
 */
@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글을 생성합니다.
     *
     * @param scheduleId 댓글을 달 일정 ID
     * @param commentRequest 생성할 댓글 정보
     * @param request HttpServletRequest 객체 (세션 조회용)
     * @return 생성된 댓글 정보와 HTTP 상태 201(CREATED)
     */
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable("scheduleId") Long scheduleId,
            @Valid @RequestBody CommentCreateRequest commentRequest,
            HttpServletRequest request
    ) {
        Long sessionUserId = SessionUtils.getUserId(request);

        return new ResponseEntity<>(commentService.createComment(scheduleId, commentRequest, sessionUserId), HttpStatus.CREATED);
    }

    /**
     * 일정에 달린 모든 댓글을 조회합니다.
     *
     * @param scheduleId 댓글 조회 대상 일정 ID
     * @return 댓글 목록과 HTTP 상태 200(OK)
     */
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getCommentsByScheduleId(@PathVariable Long scheduleId) {
        return new ResponseEntity<>(commentService.getCommentsByScheduleId(scheduleId), HttpStatus.OK);
    }

    /**
     * 댓글을 수정합니다.
     *
     * @param scheduleId 댓글이 속한 일정 ID
     * @param commentId 수정할 댓글 ID
     * @param commentRequest 수정할 댓글 정보
     * @param httpRequest HttpServletRequest 객체 (세션 조회용)
     * @return 수정된 댓글 정보와 HTTP 상태 200(OK)
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CommentUpdateRequest commentRequest,
            HttpServletRequest httpRequest
    ) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);

        return new ResponseEntity<>(commentService.updateComment(scheduleId, commentId, commentRequest, sessionUserId), HttpStatus.OK);
    }

    /**
     * 댓글을 삭제합니다.
     *
     * @param scheduleId 댓글이 속한 일정 ID
     * @param commentId 삭제할 댓글 ID
     * @param httpRequest HttpServletRequest 객체 (세션 조회용)
     * @return HTTP 상태 200(OK)
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("commentId") Long commentId,
            HttpServletRequest httpRequest
    ) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);
        commentService.deleteComment(scheduleId, commentId, sessionUserId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
