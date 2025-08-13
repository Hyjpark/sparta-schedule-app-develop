package org.example.scheduleapiv2.comment.repository;

import org.example.scheduleapiv2.comment.entity.Comment;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByScheduleId(Long scheduleId);

    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(() ->
                new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));
    }
}
