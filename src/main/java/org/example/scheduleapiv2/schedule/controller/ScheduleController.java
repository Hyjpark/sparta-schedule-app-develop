package org.example.scheduleapiv2.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.example.scheduleapiv2.schedule.dto.ScheduleCreateRequest;
import org.example.scheduleapiv2.schedule.dto.ScheduleResponse;
import org.example.scheduleapiv2.schedule.dto.ScheduleUpdateRequest;
import org.example.scheduleapiv2.schedule.service.ScheduleService;
import org.example.scheduleapiv2.user.entity.User;
import org.example.scheduleapiv2.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody ScheduleCreateRequest request, HttpServletRequest httpRequest) {
        Long userId = SessionUtils.getUserId(httpRequest);

        return new ResponseEntity<>(scheduleService.createSchedule(request, userId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAllSchedules() {
        return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> findScheduleById(@PathVariable("scheduleId") Long scheduleId) {
        return new ResponseEntity<>(scheduleService.findScheduleById(scheduleId), HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(@PathVariable("scheduleId") Long scheduleId, @RequestBody ScheduleUpdateRequest request) {
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("scheduleId") Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
