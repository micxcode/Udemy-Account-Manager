package com.avenuecode.udemy.account.manager.consumers

import com.avenuecode.udemy.account.manager.AbstractIntegTest
import com.avenuecode.udemy.account.manager.config.JmsProperties
import com.avenuecode.udemy.account.manager.dto.ScheduleIdDTO
import com.avenuecode.udemy.account.manager.service.ScheduleService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jms.core.JmsTemplate
import org.springframework.test.context.TestPropertySource
import spock.util.concurrent.AsyncConditions

@TestPropertySource(properties = "avenue-code.udemy.jms.activeSchedule.consumer.enabled=true")
class ActiveSchedulesConsumerIntegTest extends AbstractIntegTest{
    @SpringBean
    ScheduleService scheduleService = Mock()
    @Autowired
    @Qualifier("udemyJmsTemplate") JmsTemplate jmsTemplate
    @Autowired
    JmsProperties jmsProperties

    def "A schedule posted to the ActiveSchedule queue should be received by the ActiveSchedulesConsumer"() {
        given:
        def asyncCondition = new AsyncConditions()

        def scheduleId = new ScheduleIdDTO()
        scheduleId.id = 1
        scheduleService.checkSchedule(_ as ScheduleIdDTO) >> { args ->
            asyncCondition.evaluate {
                ScheduleIdDTO dequeuedScheduleId = (ScheduleIdDTO) args[0]
                assert dequeuedScheduleId.id == scheduleId.id
            }
        }

        when:
        jmsTemplate.convertAndSend(jmsProperties.activeScheduleQueueName, scheduleId)

        then:
        asyncCondition.await()
    }
}
