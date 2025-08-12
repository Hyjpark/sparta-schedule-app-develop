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

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable("scheduleId") Long scheduleId,
            @Valid @RequestBody CommentCreateRequest commentRequest,
            HttpServletRequest request
    ) {
        Long sessionUserId = SessionUtils.getUserId(request);

        return new ResponseEntity<>(commentService.createComment(scheduleId, commentRequest, sessionUserId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getCommentsByScheduleId(@PathVariable Long scheduleId) {
        return new ResponseEntity<>(commentService.getCommentsByScheduleId(scheduleId), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CommentUpdateRequest commentRequest,
            HttpServletRequest httpRequest
    ) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);

        return new ResponseEntity<>(commentService.updateComment(commentId, commentRequest, sessionUserId), HttpStatus.OK);
    }
}
