package com.avenuecode.udemy.account.manager.resource;

import com.avenuecode.udemy.account.manager.dto.AccountDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "Account API")
@RequestMapping(value = "/api/avenuecode/udemy/account")
public interface AccountResource {

    @ApiOperation(value = "Add a new Account")
    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addAccount(@Validated @RequestBody AccountDTO accountDTO);

    @ApiOperation(value = "Update the password for a given account id")
    @PutMapping(value = "/{id}/update-password",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updatePassword(@PathVariable(name = "id") final String id,
                        @RequestBody AccountDTO accountDTO);

    @ApiOperation(value = "Return a list of accounts")
    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity accountsList();

    @ApiOperation(value = "Return a list of expired accounts")
    @GetMapping(value = "/expired-accounts",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity expiredAccounts();
}
