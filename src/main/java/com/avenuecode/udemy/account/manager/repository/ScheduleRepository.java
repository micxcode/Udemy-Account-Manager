package com.avenuecode.udemy.account.manager.repository;

import com.avenuecode.udemy.account.manager.repository.domain.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findByActive(Boolean active);
}
