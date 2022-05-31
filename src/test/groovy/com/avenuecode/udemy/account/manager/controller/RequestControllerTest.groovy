package com.avenuecode.udemy.account.manager.controller

import com.avenuecode.udemy.account.manager.dto.AccessRequestDTO
import com.avenuecode.udemy.account.manager.service.RequestService
import spock.lang.Specification

class RequestControllerTest extends Specification{

    def accessRequest = Mock(AccessRequestDTO)
    def requestService = Mock(RequestService)
    def requestController = new RequestController(requestService)

    def "create access request"() {
        when:
        requestController.accessRequest(accessRequest)
        then:
        1 * requestService.accessRequest(accessRequest)
    }
}
