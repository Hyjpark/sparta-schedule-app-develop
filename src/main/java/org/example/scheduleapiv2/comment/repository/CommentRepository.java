package org.example.scheduleapiv2.comment.repository;

import org.example.scheduleapiv2.comment.entity.Comment;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 댓글(Comment) 엔티티에 대한 DB 접근을 담당하는 Repository 인터페이스.
 *
 *
 * JpaRepository를 상속받아 기본 CRUD 기능을 제공하며,
 * 특정 일정에 대한 댓글 조회, ID 기반 조회, 생성, 삭제, 페이징 처리 등의 기능을 포함합니다.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 일정 ID에 속한 댓글 목록을 조회합니다.
     *
     * @param scheduleId 일정 ID
     * @return 댓글 목록
     */
    List<Comment> findCommentsByScheduleId(Long scheduleId);

    /**
     * 댓글 ID와 일정 ID로 댓글을 조회합니다.
     *
     * @param commentId 댓글 ID
     * @param scheduleId 일정 ID
     * @return 댓글(Optional)
     */
    Optional<Comment> findByIdAndScheduleId(Long commentId, Long scheduleId);

    /**
     * 댓글 ID로 조회 후 존재하지 않으면 {@link ApiException}을 발생시킵니다.
     *
     * @param commentId 댓글 ID
     * @return 댓글 엔티티
     * @throws ApiException 댓글이 존재하지 않을 경우
     */
    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(() ->
                new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));
    }

    /**
     * 댓글 ID와 일정 ID로 조회 후 존재하지 않으면 {@link ApiException}을 발생시킵니다.
     *
     * @param commentId 댓글 ID
     * @param scheduleId 일정 ID
     * @return 댓글 엔티티
     * @throws ApiException 댓글이 존재하지 않을 경우
     */
    default Comment findByIdAndScheduleIdOrElseThrow(Long commentId, Long scheduleId) {
        return findByIdAndScheduleId(commentId, scheduleId).orElseThrow(() ->
                new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));
    }

    /**
     * 특정 일정에 달린 댓글 수를 조회합니다.
     *
     * @param scheduleId 일정 ID
     * @return 댓글 수
     */
    int countByScheduleId(Long scheduleId);

    /**
     * 일정 ID에 따른 댓글 목록을 페이징 처리하여 조회합니다.
     *
     * @param scheduleId 일정 ID
     * @param pageable 페이징 정보
     * @return 페이징 처리된 댓글 목록
     */
    Page<Comment> findByScheduleId(Long scheduleId, Pageable pageable);

    /**
     * 특정 일정에 속한 댓글들을 삭제합니다.
     *
     * @param id 일정 ID
     */
    void deleteByScheduleId(Long id);

    /**
     * 특정 사용자가 작성한 댓글들을 삭제합니다.
     *
     * @param userId 사용자 ID
     */
    void deleteByUserId(Long userId);
}
