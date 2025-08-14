package org.example.scheduleapiv2.comment.service;

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

/**
 * 댓글(Comment) 관련 비즈니스 로직을 처리하는 서비스 클래스.
 *
 * 댓글 생성, 조회, 수정, 삭제 기능을 제공하며,
 * 사용자 검증과 권한 확인을 수행합니다.
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 댓글을 생성합니다.
     *
     * @param scheduleId 댓글을 달 일정 ID
     * @param commentRequest 생성할 댓글 정보
     * @param sessionUserId 로그인한 사용자 ID
     * @return 생성된 댓글 정보
     * @throws ApiException 로그인한 사용자가 존재하지 않을 경우
     */
    @Transactional
    public CommentResponse createComment(Long scheduleId, CommentCreateRequest commentRequest, Long sessionUserId) {
        User user = userRepository.findById(sessionUserId).orElseThrow(() ->
                new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = Comment.create(commentRequest.getContents(), user, schedule);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.of(savedComment);
    }

    /**
     * 일정에 달린 댓글 목록을 조회합니다.
     *
     * @param scheduleId 일정 ID
     * @return 댓글 목록
     */
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByScheduleId(Long scheduleId) {
        scheduleRepository.findByIdOrElseThrow(scheduleId);

        return commentRepository.findCommentsByScheduleId(scheduleId).stream()
                .map(CommentResponse::of)
                .toList();
    }

    /**
     * 댓글을 수정합니다.
     *
     * @param scheduleId 댓글이 속한 일정 ID
     * @param commentId 수정할 댓글 ID
     * @param commentRequest 수정할 댓글 정보
     * @param sessionUserId 로그인한 사용자 ID
     * @return 수정된 댓글 정보
     * @throws ApiException 댓글이 존재하지 않거나 사용자가 소유자가 아닐 경우
     */
    @Transactional
    public CommentResponse updateComment(Long scheduleId, Long commentId, CommentUpdateRequest commentRequest, Long sessionUserId) {
        Comment comment = commentRepository.findByIdAndScheduleIdOrElseThrow(commentId, scheduleId);

        SessionUtils.assertUserIsOwner(sessionUserId, comment.getUser().getId());

        comment.updateContents(commentRequest.getContents());
        Comment updatedComment = commentRepository.saveAndFlush(comment);

        return CommentResponse.of(updatedComment);
    }

    /**
     * 댓글을 삭제합니다.
     *
     * @param scheduleId 댓글이 속한 일정 ID
     * @param commentId 삭제할 댓글 ID
     * @param sessionUserId 로그인한 사용자 ID
     * @throws ApiException 댓글이 존재하지 않거나 사용자가 소유자가 아닐 경우
     */
    public void deleteComment(Long scheduleId, Long commentId, Long sessionUserId) {
        Comment comment = commentRepository.findByIdAndScheduleIdOrElseThrow(commentId, scheduleId);

        SessionUtils.assertUserIsOwner(sessionUserId, comment.getUser().getId());

        commentRepository.delete(comment);
    }
}
