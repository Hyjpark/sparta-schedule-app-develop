package org.example.scheduleapiv2.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.comment.dto.CommentCreateRequest;
import org.example.scheduleapiv2.comment.dto.CommentResponse;
import org.example.scheduleapiv2.comment.entity.Comment;
import org.example.scheduleapiv2.comment.service.CommentService;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody CommentCreateRequest commentRequest,
            HttpServletRequest request
    ) {
        Long sessionUserId = SessionUtils.getUserId(request);

        return new ResponseEntity<>(commentService.createComment(scheduleId, commentRequest, sessionUserId), HttpStatus.CREATED);
    }
}
