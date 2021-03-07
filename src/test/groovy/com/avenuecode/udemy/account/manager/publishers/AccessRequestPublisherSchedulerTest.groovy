package com.avenuecode.udemy.account.manager.publishers

import com.avenuecode.udemy.account.manager.config.JmsProperties
import com.avenuecode.udemy.account.manager.dto.AccessRequestIdDTO
import com.avenuecode.udemy.account.manager.repository.RequestRepository
import com.avenuecode.udemy.account.manager.repository.domain.Request
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification
import spock.lang.Subject

class AccessRequestPublisherSchedulerTest extends Specification{

    def jmsTemplate = Mock(JmsTemplate)
    def jmsProperties = Mock(JmsProperties)
    def accessRequestPublisher = new AccessRequestPublisher(jmsTemplate, jmsProperties)
    def requestRepository = Mock(RequestRepository)

    @Subject
    def accessRequestPublisherScheduler = new AccessRequestPublisherScheduler(requestRepository, accessRequestPublisher)

    def "run() enqueue request_id for requests not yet scheduled in queue named accessRequest"() {
        given:
        jmsProperties.getAccessRequestQueueName() >> "accessRequest"

        and:
        def request = new Request()
        request.id = 1L
        requestRepository.findByScheduled(Boolean.FALSE) >> Collections.singletonList(request)

        when:
        accessRequestPublisherScheduler.run()

        then:
        1 * jmsTemplate.convertAndSend(*_) >> { args ->
            String queueName = (String)args[0]
            AccessRequestIdDTO request_id = (AccessRequestIdDTO)args[1]
            assert queueName == "accessRequest" && request_id.id == 1L
        }
    }
}
