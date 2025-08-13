package org.example.scheduleapiv2.schedule.repository;

import org.example.scheduleapiv2.common.exception.ApiException;
import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.example.scheduleapiv2.schedule.error.ScheduleErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    default Schedule findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ApiException(ScheduleErrorCode.SCHEDULE_NOT_FOUND));
    }

    Page<Schedule> findAll(Pageable pageable);

    void deleteByUserId(Long id);
}
