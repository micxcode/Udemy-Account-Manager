package com.avenuecode.udemy.account.manager.resource;

import com.avenuecode.udemy.account.manager.dto.AccessRequestDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/avenuecode/udemy")
public interface RequestResource {
    //TODO: Review @Validated annotation - not working as expected
    @PostMapping(value = "/access-request",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity accessRequest(@Validated @RequestBody AccessRequestDTO accessRequestDTO);
}
