package com.avenuecode.udemy.account.manager.service

import com.avenuecode.udemy.account.manager.dto.AccessRequestIdDTO
import com.avenuecode.udemy.account.manager.dto.ScheduleIdDTO
import com.avenuecode.udemy.account.manager.repository.AccountRepository
import com.avenuecode.udemy.account.manager.repository.RequestRepository
import com.avenuecode.udemy.account.manager.repository.ScheduleRepository
import com.avenuecode.udemy.account.manager.repository.domain.Account
import com.avenuecode.udemy.account.manager.repository.domain.Request
import com.avenuecode.udemy.account.manager.repository.domain.Schedule
import spock.lang.Specification

class ScheduleServiceTest extends Specification{

    def scheduleRepository = Mock(ScheduleRepository)
    def accountRepository = Mock(AccountRepository)
    def requestRepository = Mock(RequestRepository)
    def scheduleService = new ScheduleService(scheduleRepository, accountRepository, requestRepository)
    def accessRequestIdDTO = Mock(AccessRequestIdDTO)
    def scheduleIdDTO = Mock(ScheduleIdDTO)

    def "createSchedule() - Request not find"() {
        given:
        requestRepository.findById(accessRequestIdDTO.getId()) >> Optional.empty()

        when:
        scheduleService.createSchedule(accessRequestIdDTO)

        then:
        thrown IllegalArgumentException
    }

    def "createSchedule() - Request already scheduled"() {
        given:
        def request = new Request()
        request.scheduled = true
        requestRepository.findById(accessRequestIdDTO.getId()) >> Optional.of(request)

        when:
        scheduleService.createSchedule(accessRequestIdDTO)

        then:
        0 * accountRepository.findUnscheduledAccount()
    }

    def "createSchedule() - No accounts available"() {
        given:
        def request = new Request()
        request.scheduled = false
        requestRepository.findById(accessRequestIdDTO.getId()) >> Optional.of(request)
        accountRepository.findUnscheduledAccount() >> Collections.emptyList()
        when:
        scheduleService.createSchedule(accessRequestIdDTO)

        then:
        0 * scheduleRepository.save(*_)
        0 * accountRepository.save(*_)
        0 * requestRepository.save(*_)
    }

    def "createSchedule() - Save error"() {
        given:
        def request = new Request()
        request.scheduled = false
        requestRepository.findById(accessRequestIdDTO.getId()) >> Optional.of(request)
        def account = new Account()
        accountRepository.findUnscheduledAccount() >> Collections.singletonList(account)
        scheduleRepository.save(*_) >> {throw (Exception)}

        when:
        scheduleService.createSchedule(accessRequestIdDTO)

        then:
        0 * MailingService.notifyUser(*_)
    }

    def "createSchedule() - Schedule created"() {
        given:
        def request = new Request()
        request.scheduled = false
        requestRepository.findById(accessRequestIdDTO.getId()) >> Optional.of(request)
        def account = new Account()
        accountRepository.findUnscheduledAccount() >> Collections.singletonList(account)

        when:
        scheduleService.createSchedule(accessRequestIdDTO)

        then:
        1 * scheduleRepository.save(*_)
        1 * accountRepository.save(account)
        1 * requestRepository.save(request)
        account.scheduled == Boolean.TRUE
        account.schedules.size() > 0
        request.scheduled == Boolean.TRUE
    }

    def "checkSchedule() - Schedule not find"() {
        given:
        scheduleRepository.findById(scheduleIdDTO.getId()) >> Optional.empty()

        when:
        scheduleService.checkSchedule(scheduleIdDTO)

        then:
        thrown IllegalArgumentException
    }

    def "checkSchedule() - Schedule not active"() {
        given:
        def schedule = new Schedule()
        schedule.active = false
        scheduleRepository.findById(scheduleIdDTO.getId()) >> Optional.of(schedule)

        when:
        scheduleService.checkSchedule(scheduleIdDTO)

        then:
        0 * accountRepository.save(*_)
        0 * scheduleRepository.save(*_)
    }

    def "checkSchedule() - Schedule not expired"() {
        given:
        def schedule = new Schedule()
        schedule.active = true
        def date = new Date()
        date.setDate(date.getDate() + 1)
        schedule.end = date
        scheduleRepository.findById(scheduleIdDTO.getId()) >> Optional.of(schedule)

        when:
        scheduleService.checkSchedule(scheduleIdDTO)

        then:
        0 * accountRepository.save(*_)
        0 * scheduleRepository.save(*_)
    }

    def "checkSchedule() - Schedule save error"() {
        given:
        def schedule = new Schedule()
        schedule.active = true
        schedule.end = new Date()
        schedule.id = 1L
        def account = new Account()
        schedule.account = account
        scheduleRepository.findById(scheduleIdDTO.getId()) >> Optional.of(schedule)
        accountRepository.save(*_) >> {throw (Exception)}

        when:
        scheduleService.checkSchedule(scheduleIdDTO)

        then:
        0 * MailingService.notifyUser(*_)
        0 * MailingService.notifyAccountManagers(*_)
    }

    def "checkSchedule() - Schedule expired"() {
        given:
        def schedule = new Schedule()
        schedule.active = true
        schedule.end = new Date()
        schedule.id = 1L
        def account = new Account()
        schedule.account = account
        scheduleRepository.findById(scheduleIdDTO.getId()) >> Optional.of(schedule)

        when:
        scheduleService.checkSchedule(scheduleIdDTO)

        then:
        1 * accountRepository.save(*_)
        1 * scheduleRepository.save(*_)
        schedule.active == Boolean.FALSE
        account.expired == Boolean.TRUE
    }
}
