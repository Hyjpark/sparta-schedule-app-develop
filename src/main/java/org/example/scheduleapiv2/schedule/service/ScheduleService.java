package org.example.scheduleapiv2.schedule.service;

import lombok.RequiredArgsConstructor;
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
    public ScheduleResponse createSchedule(ScheduleCreateRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
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
    public ScheduleResponse updateSchedule(Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        schedule.updateTitleAndContents(request.getTitle(), request.getContents());

        return ScheduleResponse.of(schedule);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(scheduleId);
        scheduleRepository.delete(schedule);
    }
}
