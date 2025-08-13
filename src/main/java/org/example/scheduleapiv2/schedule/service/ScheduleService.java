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

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleCreateRequest request, Long sessionUserId) {
        User user = userRepository.findById(sessionUserId).orElseThrow(()
                -> new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));

        Schedule schedule = Schedule.create(request.getTitle(), request.getContents(), user);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponse.of(savedSchedule);
    }

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

    @Transactional
    public ScheduleResponse updateSchedule(Long sessionUserId, Long scheduleId,ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        SessionUtils.assertUserIsOwner(sessionUserId, schedule.getUser().getId());

        schedule.updateTitleAndContents(request.getTitle(), request.getContents());
        Schedule updateSchedule = scheduleRepository.saveAndFlush(schedule);

        return ScheduleResponse.of(updateSchedule);
    }

    @Transactional
    public void deleteSchedule(Long sessionUserId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        SessionUtils.assertUserIsOwner(sessionUserId, schedule.getUser().getId());

        commentRepository.deleteByScheduleId(schedule.getId());

        scheduleRepository.delete(schedule);
    }
}
