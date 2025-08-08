package org.example.scheduleapiv2.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.schedule.dto.ScheduleCreateRequest;
import org.example.scheduleapiv2.schedule.dto.ScheduleResponse;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponse createSchedule(ScheduleCreateRequest request) {

        Schedule schedule = new Schedule(request.getTitle(), request.getContents(), request.getAuthor());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponse.of(savedSchedule);
    }

    public List<ScheduleResponse> findAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponse::of)
                .toList();
    }
}
