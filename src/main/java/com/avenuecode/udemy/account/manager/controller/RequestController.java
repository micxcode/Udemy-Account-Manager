package com.avenuecode.udemy.account.manager.controller;

import com.avenuecode.udemy.account.manager.dto.AccessRequestDTO;
import com.avenuecode.udemy.account.manager.resource.RequestResource;
import com.avenuecode.udemy.account.manager.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController implements RequestResource {

    private final RequestService requestService;

    @Autowired
    public RequestController(final RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public ResponseEntity accessRequest(final AccessRequestDTO accessRequestDTO) {
        return requestService.accessRequest(accessRequestDTO);
    }
}
