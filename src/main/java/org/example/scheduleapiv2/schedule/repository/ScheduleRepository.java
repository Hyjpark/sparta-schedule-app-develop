package org.example.scheduleapiv2.schedule.repository;

import org.example.scheduleapiv2.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
