package org.example.scheduleapiv2.comment.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.comment.dto.CommentCreateRequest;
import org.example.scheduleapiv2.comment.dto.CommentResponse;
import org.example.scheduleapiv2.comment.dto.CommentUpdateRequest;
import org.example.scheduleapiv2.comment.entity.Comment;
import org.example.scheduleapiv2.comment.repository.CommentRepository;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.schedule.repository.ScheduleRepository;
import org.example.scheduleapiv2.user.entity.User;
import org.example.scheduleapiv2.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentResponse createComment(Long scheduleId, CommentCreateRequest commentRequest, Long sessionUserId) {
        User user = userRepository.findById(sessionUserId).orElseThrow(() ->
                new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = Comment.create(commentRequest.getContents(), user, schedule);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.of(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByScheduleId(Long scheduleId) {
        scheduleRepository.findByIdOrElseThrow(scheduleId);

        return commentRepository.findCommentsByScheduleId(scheduleId).stream()
                .map(CommentResponse::of)
                .toList();
    }

    @Transactional
    public CommentResponse updateComment(Long scheduleId, Long commentId, CommentUpdateRequest commentRequest, Long sessionUserId) {
        Comment comment = commentRepository.findByIdAndScheduleIdOrElseThrow(commentId, scheduleId);

        SessionUtils.assertUserIsOwner(sessionUserId, comment.getUser().getId());

        comment.updateContents(commentRequest.getContents());
        Comment updatedComment = commentRepository.saveAndFlush(comment);

        return CommentResponse.of(updatedComment);
    }

    public void deleteComment(Long scheduleId, Long commentId, Long sessionUserId) {
        Comment comment = commentRepository.findByIdAndScheduleIdOrElseThrow(commentId, scheduleId);

        SessionUtils.assertUserIsOwner(sessionUserId, comment.getUser().getId());

        commentRepository.delete(comment);
    }
}
