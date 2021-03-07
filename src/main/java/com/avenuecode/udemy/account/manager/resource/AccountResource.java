package com.avenuecode.udemy.account.manager.resource;

import com.avenuecode.udemy.account.manager.dto.AccountDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/avenuecode/udemy/account")
public interface AccountResource {

    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity addAccount(@Validated @RequestBody AccountDTO accountDTO);

    @PutMapping(value = "/{id}/update-password",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity updatePassword(@PathVariable(name = "id") final String id,
                        @RequestBody AccountDTO accountDTO);

    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity accountsList();

    @GetMapping(value = "/expired-accounts",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity expiredAccounts();
}
