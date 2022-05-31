package com.avenuecode.udemy.account.manager.service

import com.avenuecode.udemy.account.manager.dto.AccessRequestDTO
import com.avenuecode.udemy.account.manager.publishers.AccessRequestPublisher
import com.avenuecode.udemy.account.manager.repository.RequestRepository
import org.springframework.http.HttpStatus
import spock.lang.Specification

class RequestServiceImplTest extends Specification{

    def requestRepository = Mock(RequestRepository)
    def accessRequestPublisher = Mock(AccessRequestPublisher)
    def requestServiceImpl = new RequestServiceImpl(requestRepository, accessRequestPublisher)
    def accessRequestDTO = new AccessRequestDTO("michel.fonseca@avenuecode.com", "junior", "Java", "10", "Learn")

    def "accessRequest() - Request save error"() {
        given:
        requestRepository.save(*_) >> {throw (Exception)}

        when:
        def response = requestServiceImpl.accessRequest(accessRequestDTO)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
    }

    def "accessRequest() - Request created"() {
        when:
        def response = requestServiceImpl.accessRequest(accessRequestDTO)

        then:
        response.statusCode == HttpStatus.CREATED
    }
}
