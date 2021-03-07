package com.avenuecode.udemy.account.manager.consumers

import com.avenuecode.udemy.account.manager.dto.AccessRequestIdDTO
import com.avenuecode.udemy.account.manager.service.ScheduleService
import spock.lang.Specification
import spock.lang.Subject

class AccessRequestConsumerTest extends Specification{

    def scheduleService = Mock(ScheduleService)

    @Subject
    def accessRequestConsumer = new AccessRequestConsumer(scheduleService)

    def "consume() calls createSchedule"() {
        given:
        def accessRequestIdDTO = new AccessRequestIdDTO(1L)

        when:
        accessRequestConsumer.consume(accessRequestIdDTO)

        then:
        1 * scheduleService.createSchedule(accessRequestIdDTO)
    }
}
