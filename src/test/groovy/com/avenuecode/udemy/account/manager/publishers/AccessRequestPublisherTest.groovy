package com.avenuecode.udemy.account.manager.publishers

import com.avenuecode.udemy.account.manager.config.JmsProperties
import com.avenuecode.udemy.account.manager.dto.AccessRequestIdDTO
import com.avenuecode.udemy.account.manager.repository.domain.Request
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

class AccessRequestPublisherTest extends Specification{

    def jmsTemplate = Mock(JmsTemplate)
    def jmsProperties = Mock(JmsProperties)
    def accessRequestPublisher = new AccessRequestPublisher(jmsTemplate, jmsProperties)

    def "publish() enqueue request_id in queue named accessRequest"() {
        given:
        def request = new Request()
        request.id = 1L
        jmsProperties.getAccessRequestQueueName() >> "accessRequest"

        when:
        accessRequestPublisher.publish(request)

        then:
        1 * jmsTemplate.convertAndSend(*_) >> { args ->
            String queueName = (String)args[0]
            AccessRequestIdDTO request_id = (AccessRequestIdDTO)args[1]
            assert queueName == "accessRequest" && request_id.id == 1L
        }
    }
}
