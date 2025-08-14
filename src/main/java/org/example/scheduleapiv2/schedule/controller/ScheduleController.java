package org.example.scheduleapiv2.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduleapiv2.common.util.SessionUtils;
import org.example.scheduleapiv2.schedule.dto.*;
import org.example.scheduleapiv2.schedule.service.ScheduleService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 일정(Schedule) 관련 요청을 처리하는 컨트롤러 클래스.
 *
 * 일정 생성, 조회(단일/전체), 수정, 삭제 기능을 제공합니다.
 * 로그인 세션 및 사용자 입력값 검증합니다.
 */
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정을 생성합니다.
     *
     * @param request 생성할 일정 정보
     * @param httpRequest HttpServletRequest 객체 (세션 조회용)
     * @return 생성된 일정 정보와 HTTP 상태 201(CREATED)
     */
    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@Valid @RequestBody ScheduleCreateRequest request, HttpServletRequest httpRequest) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);

        return new ResponseEntity<>(scheduleService.createSchedule(request, sessionUserId), HttpStatus.CREATED);
    }

    /**
     * 모든 일정을 페이징 처리하여 조회합니다.
     *
     * @param pageable 페이징 및 정렬 정보
     * @return 일정 목록과 HTTP 상태 200(OK)
     */
    @GetMapping
    public ResponseEntity<List<SchedulePagingResponse>> findAllSchedules(
            @PageableDefault(page = 0, size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new ResponseEntity<>(scheduleService.findAllSchedules(pageable), HttpStatus.OK);
    }

    /**
     * 특정 ID의 일정을 조회합니다.
     *
     * @param scheduleId 일정 ID
     * @param pageable 댓글 페이징 정보
     * @return 댓글과 함께 조회된 일정 정보와 HTTP 상태 200(OK)
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleWithCommentResponse> findScheduleById(
            @PathVariable("scheduleId") Long scheduleId,
            @PageableDefault(page = 0, size = 10, sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new ResponseEntity<>(scheduleService.findScheduleById(scheduleId, pageable), HttpStatus.OK);
    }

    /**
     * 특정 일정을 수정합니다.
     *
     * @param scheduleId 수정할 일정 ID
     * @param request 수정할 일정 정보
     * @param httpRequest HttpServletRequest 객체 (세션 조회용)
     * @return 수정된 일정 정보와 HTTP 상태 200(OK)
     */
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @Valid @RequestBody ScheduleUpdateRequest request,
            HttpServletRequest httpRequest
    ) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);

        return new ResponseEntity<>(scheduleService.updateSchedule(sessionUserId, scheduleId, request), HttpStatus.OK);
    }

    /**
     * 특정 일정을 삭제합니다.
     *
     * @param scheduleId 삭제할 일정 ID
     * @param httpRequest HttpServletRequest 객체 (세션 조회용)
     * @return HTTP 상태 200(OK)
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("scheduleId") Long scheduleId, HttpServletRequest httpRequest) {
        Long sessionUserId = SessionUtils.getUserId(httpRequest);

        scheduleService.deleteSchedule(sessionUserId, scheduleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
