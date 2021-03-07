package com.avenuecode.udemy.account.manager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsProperties {

    @Getter
    @Value("${avenue-code.udemy.jms.accessRequest.queueName}")
    private String accessRequestQueueName;

    @Getter
    @Value("${avenue-code.udemy.jms.activeSchedule.queueName}")
    private String activeScheduleQueueName;
}
