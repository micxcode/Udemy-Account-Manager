package com.avenuecode.udemy.account.manager.publishers;

import com.avenuecode.udemy.account.manager.config.JmsProperties;
import com.avenuecode.udemy.account.manager.dto.AccessRequestIdDTO;
import com.avenuecode.udemy.account.manager.repository.domain.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccessRequestPublisher {

    private final JmsTemplate jmsTemplate;
    private final JmsProperties jmsProperties;

    public AccessRequestPublisher(@Qualifier("udemyJmsTemplate") JmsTemplate jmsTemplate,
                                  JmsProperties jmsProperties) {
        this.jmsTemplate = jmsTemplate;
        this.jmsProperties = jmsProperties;
    }

    public void publish(Request request) {
        String queueName = jmsProperties.getAccessRequestQueueName();
        if (log.isInfoEnabled()) {
            log.info("Publishing request '{}' to '{}'", request, queueName);
        }
        try {
            AccessRequestIdDTO accessRequestIdDTO = new AccessRequestIdDTO(request.getId());
            jmsTemplate.convertAndSend(queueName, accessRequestIdDTO);
        } catch (Exception e) {
            log.error("Error when trying to publish request {} to {}", request, queueName, e);
        }
    }
}
