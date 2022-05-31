package com.avenuecode.udemy.account.manager.consumers;

import com.avenuecode.udemy.account.manager.dto.ScheduleIdDTO;
import com.avenuecode.udemy.account.manager.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "avenue-code.udemy.jms.activeSchedule.consumer.enabled",
        havingValue = "true",
        matchIfMissing = true)
@Slf4j
public class ActiveSchedulesConsumer {

    private ScheduleService scheduleService;

    public ActiveSchedulesConsumer(final ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @JmsListener(containerFactory = "udemyJmsListener", destination = "${avenue-code.udemy.jms.activeSchedule.queueName}")
    public void consume(final ScheduleIdDTO scheduleIdDTO) {
        log.info("Consuming active schedule '{}'", scheduleIdDTO.toString());
        scheduleService.checkSchedule(scheduleIdDTO);
    }
}
