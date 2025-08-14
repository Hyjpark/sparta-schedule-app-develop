package org.example.scheduleapiv2.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.comment.dto.CommentPagingResponse;
import org.example.scheduleapiv2.comment.entity.Comment;
import org.example.scheduleapiv2.comment.repository.CommentRepository;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.example.scheduleapiv2.schedule.dto.*;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.schedule.repository.ScheduleRepository;
import org.example.scheduleapiv2.user.entity.User;
import org.example.scheduleapiv2.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 일정(Schedule) 관련 비즈니스 로직을 처리하는 서비스 클래스.
 *
 * 일정 생성, 조회, 수정, 삭제 기능을 제공하며,
 * 댓글 개수 조회 및 일정 소유자 권한 검증을 포함합니다.
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 새로운 일정을 생성합니다.
     *
     * @param request 일정 생성 요청 데이터
     * @param sessionUserId 로그인한 사용자 ID
     * @return 생성된 일정 정보
     * @throws ApiException 로그인한 사용자가 존재하지 않을 경우
     */
    @Transactional
    public ScheduleResponse createSchedule(ScheduleCreateRequest request, Long sessionUserId) {
        User user = userRepository.findById(sessionUserId).orElseThrow(()
                -> new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));

        Schedule schedule = Schedule.create(request.getTitle(), request.getContents(), user);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponse.of(savedSchedule);
    }

    /**
     * 모든 일정을 페이징 처리하여 조회합니다.
     *
     * @param pageable 페이징 정보
     * @return 일정과 댓글 개수, 작성자명 포함한 페이징 응답 목록
     */
    @Transactional(readOnly = true)
    public List<SchedulePagingResponse> findAllSchedules(Pageable pageable) {
        return scheduleRepository.findAll(pageable).getContent().stream()
                .map(schedule -> {
                    int commentCount = commentRepository.countByScheduleId(schedule.getId());
                    User user = userRepository.findByIdOrElseThrow(schedule.getUser().getId());
                    return SchedulePagingResponse.of(schedule, commentCount, user.getName());
                })
                .toList();
    }

    /**
     * 특정 일정과 해당 일정의 댓글 목록을 조회합니다.
     *
     * @param scheduleId 조회할 일정 ID
     * @param pageable 댓글 페이징 정보
     * @return 일정과 댓글을 포함한 상세 응답
     * @throws ApiException 일정이 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public ScheduleWithCommentResponse findScheduleById(Long scheduleId, Pageable pageable) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        List<CommentPagingResponse> commentPage = commentRepository.findByScheduleId(scheduleId, pageable).getContent().stream()
                .map(comment -> {
                    User user = userRepository.findByIdOrElseThrow(comment.getUser().getId());
                    return CommentPagingResponse.of(comment, user.getName());
                })
                .toList();

        return ScheduleWithCommentResponse.of(schedule, commentPage);
    }

    /**
     * 일정 제목과 내용을 수정합니다.
     *
     * @param sessionUserId 로그인한 사용자 ID
     * @param scheduleId 수정할 일정 ID
     * @param request 수정할 일정 정보
     * @return 수정된 일정 정보
     * @throws ApiException 일정이 존재하지 않거나 사용자가 소유자가 아닐 경우
     */
    @Transactional
    public ScheduleResponse updateSchedule(Long sessionUserId, Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        SessionUtils.assertUserIsOwner(sessionUserId, schedule.getUser().getId());

        schedule.updateTitleAndContents(request.getTitle(), request.getContents());
        Schedule updateSchedule = scheduleRepository.saveAndFlush(schedule);

        return ScheduleResponse.of(updateSchedule);
    }

    /**
     * 일정을 삭제하고, 해당 일정에 달린 댓글도 함께 삭제합니다.
     *
     * @param sessionUserId 로그인한 사용자 ID
     * @param scheduleId 삭제할 일정 ID
     * @throws ApiException 일정이 존재하지 않거나 사용자가 소유자가 아닐 경우
     */
    @Transactional
    public void deleteSchedule(Long sessionUserId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        SessionUtils.assertUserIsOwner(sessionUserId, schedule.getUser().getId());

        commentRepository.deleteByScheduleId(schedule.getId());

        scheduleRepository.delete(schedule);
    }
}
