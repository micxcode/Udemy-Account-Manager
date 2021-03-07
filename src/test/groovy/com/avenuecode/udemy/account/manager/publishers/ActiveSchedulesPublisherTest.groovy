package com.avenuecode.udemy.account.manager.publishers

import com.avenuecode.udemy.account.manager.config.JmsProperties
import com.avenuecode.udemy.account.manager.dto.ScheduleIdDTO
import com.avenuecode.udemy.account.manager.repository.domain.Schedule
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

class ActiveSchedulesPublisherTest extends Specification{

    def jmsTemplate = Mock(JmsTemplate)
    def jmsProperties = Mock(JmsProperties)
    def activeSchedulesPublisher = new ActiveSchedulesPublisher(jmsTemplate, jmsProperties)

    def "publish() enqueue schedule_id in queue named activeSchedule"() {
        given:
        def schedule = new Schedule()
        schedule.id = 1L
        jmsProperties.getActiveScheduleQueueName() >> "activeSchedule"

        when:
        activeSchedulesPublisher.publish(schedule)

        then:
        1 * jmsTemplate.convertAndSend(*_) >> { args ->
            String queueName = (String)args[0]
            ScheduleIdDTO schedule_id = (ScheduleIdDTO)args[1]
            assert queueName == "activeSchedule" && schedule_id.id == 1L
        }
    }
}
