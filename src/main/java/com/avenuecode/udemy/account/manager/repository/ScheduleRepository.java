package com.avenuecode.udemy.account.manager.repository;

import com.avenuecode.udemy.account.manager.repository.domain.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findByActive(Boolean active);
}
