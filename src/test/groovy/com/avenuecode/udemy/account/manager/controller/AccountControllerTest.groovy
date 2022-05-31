package com.avenuecode.udemy.account.manager.controller

import com.avenuecode.udemy.account.manager.dto.AccountDTO
import com.avenuecode.udemy.account.manager.service.AccountService
import spock.lang.Specification

class AccountControllerTest extends Specification{

    def accountService = Mock(AccountService)
    def accountDTO = Mock(AccountDTO)
    def accountController = new AccountController(accountService)

    def "add account"() {
        when:
        accountController.addAccount(accountDTO)
        then:
        1 * accountService.addAccount(accountDTO)
    }

    def "update passowrd"() {
        when:
        accountController.updatePassword("account_id", accountDTO)
        then:
        1 * accountService.updatePassword("account_id", accountDTO)
    }

    def "accounts list"() {
        when:
        accountController.accountsList()
        then:
        1 * accountService.accountsList()
    }

    def "unscheduled accounts list"() {
        when:
        accountController.expiredAccounts()
        then:
        1 * accountService.expiredAccounts()
    }
}
