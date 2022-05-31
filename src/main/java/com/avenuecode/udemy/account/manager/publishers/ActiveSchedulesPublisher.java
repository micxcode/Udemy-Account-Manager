package com.avenuecode.udemy.account.manager.publishers;

import com.avenuecode.udemy.account.manager.config.JmsProperties;
import com.avenuecode.udemy.account.manager.dto.ScheduleIdDTO;
import com.avenuecode.udemy.account.manager.repository.domain.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveSchedulesPublisher {

    private final JmsTemplate jmsTemplate;
    private final JmsProperties jmsProperties;

    public ActiveSchedulesPublisher(@Qualifier("udemyJmsTemplate") JmsTemplate jmsTemplate,
                                    JmsProperties jmsProperties) {
        this.jmsTemplate = jmsTemplate;
        this.jmsProperties = jmsProperties;
    }

    public void publish(Schedule schedule){
        String queueName = jmsProperties.getActiveScheduleQueueName();
        if (log.isInfoEnabled()) {
            log.info("Publishing active schedule '{}' to '{}'", schedule, queueName);
        }
        try {
            ScheduleIdDTO scheduleIdDTO = new ScheduleIdDTO(schedule.getId());
            jmsTemplate.convertAndSend(queueName, scheduleIdDTO);
        } catch (Exception e) {
            log.error("Error when trying to publish active schedule {} to {}", schedule, queueName, e);
        }
    }
}
