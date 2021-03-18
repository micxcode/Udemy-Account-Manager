package com.avenuecode.udemy.account.manager.controller;

import com.avenuecode.udemy.account.manager.dto.AccountDTO;
import com.avenuecode.udemy.account.manager.resource.AccountResource;
import com.avenuecode.udemy.account.manager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountResource {

    private final AccountService accountService;

    @Autowired
    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity addAccount(final AccountDTO accountDTO) {
        return accountService.addAccount(accountDTO);
    }

    @Override
    public ResponseEntity updatePassword(final String id, final AccountDTO accountDTO) {
        return accountService.updatePassword(id, accountDTO);
    }

    @Override
    public ResponseEntity accountsList() {
        return accountService.accountsList();
    }

    @Override
    public ResponseEntity expiredAccounts() {
        return accountService.expiredAccounts();
    }
}
