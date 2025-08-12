package org.example.scheduleapiv2.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.error.GlobalErrorCode;
import org.example.scheduleapiv2.common.exception.ApiException;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.example.scheduleapiv2.schedule.dto.ScheduleCreateRequest;
import org.example.scheduleapiv2.schedule.dto.ScheduleResponse;
import org.example.scheduleapiv2.schedule.dto.ScheduleUpdateRequest;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.schedule.repository.ScheduleRepository;
import org.example.scheduleapiv2.user.entity.User;
import org.example.scheduleapiv2.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleCreateRequest request, Long sessionUserId) {
        User user = userRepository.findById(sessionUserId).orElseThrow(()
                -> new ApiException(GlobalErrorCode.RESOURCE_NOT_FOUND));

        Schedule schedule = new Schedule(request.getTitle(), request.getContents(), user);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponse.of(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScheduleResponse findScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        return ScheduleResponse.of(schedule);
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

        scheduleRepository.delete(schedule);
    }
}
