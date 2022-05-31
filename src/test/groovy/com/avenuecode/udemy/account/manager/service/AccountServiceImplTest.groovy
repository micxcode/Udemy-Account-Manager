package com.avenuecode.udemy.account.manager.service

import com.avenuecode.udemy.account.manager.dto.AccountDTO
import com.avenuecode.udemy.account.manager.repository.AccountRepository
import com.avenuecode.udemy.account.manager.repository.domain.Account
import org.springframework.http.HttpStatus
import spock.lang.Specification

class AccountServiceImplTest extends Specification{

    def accountRepository = Mock(AccountRepository)
    def accountServiceImpl = new AccountServiceImpl(accountRepository)
    def accountDTO = new AccountDTO("avenuecode@avenuecode.com", "pwd", false)


    def "addAccount() - Account already exists"() {
        given:
        accountRepository.findByEmail(accountDTO.getEmail()) >> new Account()

        when:
        def response = accountServiceImpl.addAccount(accountDTO)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Email already registered."

    }

    def "addAccount() - Account save error"() {
        given:
        accountRepository.save(*_) >> {throw (Exception)}

        when:
        def response = accountServiceImpl.addAccount(accountDTO)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR

    }

    def "addAccount() - Account created"() {
        when:
        def response = accountServiceImpl.addAccount(accountDTO)

        then:
        response.statusCode == HttpStatus.CREATED
    }

//    def "updatePassword() - Account not found"() {
//        given:
//        def id = "1"
//        accountRepository.findById(Long.parseLong(id)) >> new Optional<Account>()
//
//        when:
//        def response = accountServiceImpl.updatePassword(id, accountDTO)
//
//        then:
//        response.statusCode == HttpStatus.BAD_REQUEST
//    }

    def "updatePassword() - Parse error"() {
        given:
        def id = "account_id"

        when:
        def response = accountServiceImpl.updatePassword(id, accountDTO)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
    }

//    def "updatePassword() - Email not match id"() {
//        given:
//        def id = "1"
//        def account = new Account()
//        account.email = "test@avenuecode.com"
//        accountRepository.findById(Long.parseLong(id)) >> Optional.of(account)
//
//        when:
//        def response = accountServiceImpl.updatePassword(id, accountDTO)
//
//        then:
//        response.statusCode == HttpStatus.BAD_REQUEST
//    }

    def "updatePassword() - Password updated"() {
        given:
        def id = "1"
        def account = new Account()
        account.email = accountDTO.getEmail()
//        accountRepository.findById(Long.parseLong(id)) >> Optional.of(account)

        when:
        def response = accountServiceImpl.updatePassword(id, accountDTO)

        then:
        response.statusCode == HttpStatus.NO_CONTENT
    }

    def "updatePassword() - Account save error"() {
        given:
        def id = "1"
//        def account = new Account()
//        account.email = accountDTO.getEmail()
//        accountRepository.findById(Long.parseLong(id)) >> Optional.of(account)
        accountRepository.updateAccount(*_, *_) >> {throw (Exception)}

        when:
        def response = accountServiceImpl.updatePassword(id, accountDTO)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
    }

    def "accountsList() - Return empty"() {
        given:
        accountRepository.findAll() >> Collections.emptyList()

        when:
        def response = accountServiceImpl.accountsList()

        then:
        response.statusCode == HttpStatus.OK
    }

    def "accountsList() - Return list"() {
        given:
        def account = new Account()
        account.email = accountDTO.getEmail()
        account.password = accountDTO.getPassword()
        account.scheduled = accountDTO.getScheduled()
        accountRepository.findAll() >> Collections.singletonList(account)

        when:
        def response = accountServiceImpl.accountsList()

        then:
        def responseData = response.body[0] as AccountDTO
        responseData.email == account.getEmail()
        responseData.password == account.getPassword()
        responseData.scheduled == account.getScheduled()
        response.statusCode == HttpStatus.OK
    }

    def "expiredAccounts() - Return empty"() {
        given:
        accountRepository.findByExpired(Boolean.TRUE) >> Collections.emptyList()

        when:
        def response = accountServiceImpl.expiredAccounts()

        then:
        response.statusCode == HttpStatus.OK
    }

    def "expiredAccounts() - Return list"() {
        given:
        def account = new Account()
        account.email = accountDTO.getEmail()
        account.password = accountDTO.getPassword()
        account.scheduled = accountDTO.getScheduled()
        account.expired = Boolean.TRUE
        accountRepository.findByExpired(Boolean.TRUE) >> Collections.singletonList(account)

        when:
        def response = accountServiceImpl.expiredAccounts()

        then:
        def responseData = response.body[0] as AccountDTO
        responseData.email == account.getEmail()
        responseData.password == account.getPassword()
        responseData.scheduled == account.getScheduled()
        response.statusCode == HttpStatus.OK
    }
}
