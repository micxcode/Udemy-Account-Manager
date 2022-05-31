package com.avenuecode.udemy.account.manager.consumers

import com.avenuecode.udemy.account.manager.AbstractIntegTest
import com.avenuecode.udemy.account.manager.config.JmsProperties
import com.avenuecode.udemy.account.manager.dto.AccessRequestIdDTO
import com.avenuecode.udemy.account.manager.service.ScheduleService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jms.core.JmsTemplate
import org.springframework.test.context.TestPropertySource
import spock.util.concurrent.AsyncConditions

@TestPropertySource(properties = "avenue-code.udemy.jms.accessRequest.consumer.enabled=true")
class AccessRequestConsumerIntegTest extends AbstractIntegTest{
    @SpringBean
    ScheduleService scheduleService = Mock()
    @Autowired
    @Qualifier("udemyJmsTemplate") JmsTemplate jmsTemplate
    @Autowired
    JmsProperties jmsProperties

    def "A request posted to the AccessRequest queue should be received by the AccessRequestConsumer"() {
        given:
        def asyncCondition = new AsyncConditions()

        def accessRequestId = new AccessRequestIdDTO()
        accessRequestId.id = 1
        scheduleService.createSchedule(_ as AccessRequestIdDTO) >> { args ->
            asyncCondition.evaluate {
                AccessRequestIdDTO dequeuedRequestId = (AccessRequestIdDTO) args[0]
                assert dequeuedRequestId.id == accessRequestId.id
            }
        }

        when:
        jmsTemplate.convertAndSend(jmsProperties.accessRequestQueueName, accessRequestId)

        then:
        asyncCondition.await()
    }
}
