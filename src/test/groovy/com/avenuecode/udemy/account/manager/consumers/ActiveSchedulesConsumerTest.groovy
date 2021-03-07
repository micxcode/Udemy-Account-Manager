package com.avenuecode.udemy.account.manager.consumers

import com.avenuecode.udemy.account.manager.dto.ScheduleIdDTO
import com.avenuecode.udemy.account.manager.service.ScheduleService
import spock.lang.Specification

class ActiveSchedulesConsumerTest extends Specification{

    def scheduleService = Mock(ScheduleService)
    def activeSchedulesConsumer = new ActiveSchedulesConsumer(scheduleService)

    def "consume() calls checkSchedule"() {
        given:
        def scheduleIdDTO = new ScheduleIdDTO(1L)

        when:
        activeSchedulesConsumer.consume(scheduleIdDTO)

        then:
        1 * scheduleService.checkSchedule(scheduleIdDTO)
    }
}
