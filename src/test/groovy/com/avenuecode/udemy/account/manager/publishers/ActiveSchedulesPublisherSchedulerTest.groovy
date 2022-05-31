package com.avenuecode.udemy.account.manager.publishers

import com.avenuecode.udemy.account.manager.config.JmsProperties
import com.avenuecode.udemy.account.manager.dto.ScheduleIdDTO
import com.avenuecode.udemy.account.manager.repository.ScheduleRepository
import com.avenuecode.udemy.account.manager.repository.domain.Schedule
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification
import spock.lang.Subject

class ActiveSchedulesPublisherSchedulerTest extends Specification{

    def jmsTemplate = Mock(JmsTemplate)
    def jmsProperties = Mock(JmsProperties)
    def activeSchedulesPublisher = new ActiveSchedulesPublisher(jmsTemplate, jmsProperties)
    def scheduleRepository = Mock(ScheduleRepository)

    @Subject
    def activeSchedulesPublisherScheduler = new ActiveSchedulesPublisherScheduler(scheduleRepository, activeSchedulesPublisher)

    def "run() enqueue schedule_id for active schedules in queue named activeSchedule"() {
        given:
        jmsProperties.getActiveScheduleQueueName() >> "activeSchedule"

        and:
        def schedule = new Schedule()
        schedule.id = 1L
        scheduleRepository.findByActive(Boolean.TRUE) >> Collections.singletonList(schedule)

        when:
        activeSchedulesPublisherScheduler.run()

        then:
        1 * jmsTemplate.convertAndSend(*_) >> { args ->
            String queueName = (String)args[0]
            ScheduleIdDTO schedule_id = (ScheduleIdDTO)args[1]
            assert queueName == "activeSchedule" && schedule_id.id == 1L
        }
    }
}
