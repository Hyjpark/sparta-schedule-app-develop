package org.example.scheduleapiv2.schedule.repository;

import org.example.scheduleapiv2.common.exception.ApiException;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.schedule.error.ScheduleErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 일정(Schedule) 엔티티에 대한 DB 접근을 담당하는 Repository 인터페이스.
 *
 * JpaRepository를 상속받아 기본 CRUD 기능을 제공하며,
 * 일정 조회, 페이징, 작성자 기준 삭제 등의 기능을 포함합니다.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * 주어진 ID의 일정을 조회합니다.
     * 일정이 존재하지 않으면 {@link ApiException}을 발생시킵니다.
     *
     * @param id 조회할 일정 ID
     * @return Schedule 엔티티
     * @throws ApiException 일정이 존재하지 않을 경우
     */
    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ApiException(ScheduleErrorCode.SCHEDULE_NOT_FOUND));
    }

    /**
     * 모든 일정을 페이징 처리하여 조회합니다.
     *
     * @param pageable 페이징 정보
     * @return 페이징 처리된 Schedule 목록
     */
    Page<Schedule> findAll(Pageable pageable);

    /**
     * 특정 사용자가 작성한 모든 일정을 삭제합니다.
     *
     * @param id 사용자 ID
     */
    void deleteByUserId(Long id);
}
