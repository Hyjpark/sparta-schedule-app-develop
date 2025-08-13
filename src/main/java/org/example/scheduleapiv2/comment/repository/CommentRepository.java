package org.example.scheduleapiv2.comment.repository;

import org.example.scheduleapiv2.comment.entity.Comment;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByScheduleId(Long scheduleId);

    Optional<Comment> findByIdAndScheduleId(Long commentId, Long scheduleId);

    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(() ->
                new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));
    }

    default Comment findByIdAndScheduleIdOrElseThorw(Long commentId, Long scheduleId) {
        return findByIdAndScheduleId(commentId, scheduleId).orElseThrow(() ->
                new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));
    }

    int countByScheduleId(Long scheduleId);

    Page<Comment> findByScheduleId(Long scheduleId, Pageable pageable);

    void deleteByScheduleId(Long id);

    void deleteByUserId(Long userId);
}
