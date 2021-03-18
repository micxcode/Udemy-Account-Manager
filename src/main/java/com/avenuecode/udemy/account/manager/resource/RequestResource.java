package com.avenuecode.udemy.account.manager.resource;

import com.avenuecode.udemy.account.manager.dto.AccessRequestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "Access Request API")
@RequestMapping(value = "/api/avenuecode/udemy")
public interface RequestResource {
    //TODO: Review @Validated annotation - not working as expected
    @ApiOperation(value = "Creates a request to access an Udemy Account")
    @PostMapping(value = "/access-request",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity accessRequest(@Validated @RequestBody AccessRequestDTO accessRequestDTO);
}
