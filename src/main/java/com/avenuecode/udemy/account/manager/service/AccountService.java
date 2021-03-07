package com.avenuecode.udemy.account.manager.service;

import com.avenuecode.udemy.account.manager.dto.AccountDTO;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity addAccount(final AccountDTO accountDTO);

    ResponseEntity updatePassword(final String id, final AccountDTO accountDTO);

    ResponseEntity accountsList();

    ResponseEntity expiredAccounts();
}
