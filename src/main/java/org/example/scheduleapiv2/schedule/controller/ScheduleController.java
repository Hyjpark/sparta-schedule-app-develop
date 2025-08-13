package org.example.scheduleapiv2.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.example.scheduleapiv2.schedule.dto.ScheduleCreateRequest;
import org.example.scheduleapiv2.schedule.dto.SchedulePagingResponse;
import org.example.scheduleapiv2.schedule.dto.ScheduleResponse;
import org.example.scheduleapiv2.schedule.dto.ScheduleUpdateRequest;
import org.example.scheduleapiv2.schedule.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<ScheduleResponse> createSchedule(@Valid @RequestBody ScheduleCreateRequest request, HttpServletRequest httpRequest) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);

        return new ResponseEntity<>(scheduleService.createSchedule(request, sessionUserId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SchedulePagingResponse>> findAllSchedules(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ResponseEntity<>(scheduleService.findAllSchedules(pageRequest), HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> findScheduleById(@PathVariable("scheduleId") Long scheduleId) {
        return new ResponseEntity<>(scheduleService.findScheduleById(scheduleId), HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequest request,
            HttpServletRequest httpRequest
    ) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);
        return new ResponseEntity<>(scheduleService.updateSchedule(sessionUserId, scheduleId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("scheduleId") Long scheduleId, HttpServletRequest httpRequest) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);
        scheduleService.deleteSchedule(sessionUserId, scheduleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
