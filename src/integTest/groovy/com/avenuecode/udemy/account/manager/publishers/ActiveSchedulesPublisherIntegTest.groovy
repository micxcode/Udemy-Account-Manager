package com.avenuecode.udemy.account.manager.publishers

import com.avenuecode.udemy.account.manager.AbstractIntegTest
import com.avenuecode.udemy.account.manager.repository.ScheduleRepository
import com.avenuecode.udemy.account.manager.repository.domain.Schedule
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jms.core.JmsTemplate
import org.springframework.test.context.TestPropertySource

@TestPropertySource(properties = "avenue-code.udemy.jms.activeSchedule.scheduler.enabled=true")
class ActiveSchedulesPublisherIntegTest extends AbstractIntegTest{
    @Autowired
    ScheduleRepository scheduleRepository
    @SpringBean
    @Qualifier("udemyJmsTemplate") JmsTemplate jmsTemplate = Mock()

    def cleanup() {
        scheduleRepository.deleteAll()
    }

    def "A active schedule should be returned by findByActive()"() {
        def schedule = new Schedule()
        schedule.active = true
        scheduleRepository.save(schedule)

        when:
        def schedules = scheduleRepository.findByActive(Boolean.TRUE)

        then:
        assert schedules.size() == 1
    }
}
