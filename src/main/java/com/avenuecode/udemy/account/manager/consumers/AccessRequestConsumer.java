package com.avenuecode.udemy.account.manager.consumers;

import com.avenuecode.udemy.account.manager.dto.AccessRequestIdDTO;
import com.avenuecode.udemy.account.manager.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "avenue-code.udemy.jms.accessRequest.consumer.enabled",
        havingValue = "true",
        matchIfMissing = true)
@Slf4j
public class AccessRequestConsumer {

    private ScheduleService scheduleService;

    public AccessRequestConsumer(final ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @JmsListener(containerFactory = "udemyJmsListener", destination = "${avenue-code.udemy.jms.accessRequest.queueName}")
    public void consume(final AccessRequestIdDTO accessRequestIdDTO) {
        log.info("Consuming request '{}'", accessRequestIdDTO.toString());
        scheduleService.createSchedule(accessRequestIdDTO);
    }
}
