package com.avenuecode.udemy.account.manager.publishers;

import com.avenuecode.udemy.account.manager.repository.ScheduleRepository;
import com.avenuecode.udemy.account.manager.repository.domain.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(
        value = "avenue-code.udemy.jms.activeSchedule.scheduler.enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableAsync(proxyTargetClass = true)
@Slf4j
public class ActiveSchedulesPublisherScheduler implements Runnable{

    private final ScheduleRepository scheduleRepository;
    private final ActiveSchedulesPublisher activeSchedulesPublisher;

    public ActiveSchedulesPublisherScheduler(final ScheduleRepository scheduleRepository,
                                             final ActiveSchedulesPublisher activeSchedulesPublisher) {
        this.scheduleRepository = scheduleRepository;
        this.activeSchedulesPublisher = activeSchedulesPublisher;
        log.info("Initializing ActiveSchedulesPublisherScheduler...");
    }

    @Override
    @Async
    @Scheduled(fixedRateString = "${avenue-code.udemy.jms.activeSchedule.pollingInterval}")
    public void run() {
        List<Schedule> schedules = scheduleRepository.findByActive(Boolean.TRUE);
        schedules.forEach(activeSchedulesPublisher::publish);
    }
}
