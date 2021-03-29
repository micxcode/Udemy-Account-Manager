package com.avenuecode.udemy.account.manager.service;

import com.avenuecode.udemy.account.manager.dto.AccountDTO;
import com.avenuecode.udemy.account.manager.repository.AccountRepository;
import com.avenuecode.udemy.account.manager.repository.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseEntity addAccount(final AccountDTO accountDTO) {
        log.info("Registering new account={}", accountDTO.getEmail());

        Account existingAccount = accountRepository.findByEmail(accountDTO.getEmail());

        if(existingAccount != null){
            log.warn("Account with email={} already exists", accountDTO.getEmail());
            return new ResponseEntity("Email already registered.", HttpStatus.BAD_REQUEST);
        }

        try{
            Account account = new Account(accountDTO.getEmail(), accountDTO.getPassword());
            log.debug("Saving new account={}", account);
            accountRepository.save(account);
        }
        catch (Exception e){
            log.error("Error registering new account={}", accountDTO.getEmail(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Account={} registered successfully", accountDTO.getEmail());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updatePassword(final String id, final AccountDTO accountDTO) {
        log.info("Updating account={} password", accountDTO.getEmail());

        Long accountId;

        try{
            accountId = Long.parseLong(id);
        }
        catch (NumberFormatException e){
            log.warn("Couldn't parse id={}", id);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //TODO: Is this needed?
//        Optional<Account> account = accountRepository.findById(accountId);
//
//        if(!account.isPresent() || !account.get().getEmail().equals(accountDTO.getEmail())){
//            log.info("Couldn't find Account={} for id={}", accountDTO.getEmail(), id);
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }

        try{
//            account.get().setPassword(accountDTO.getPassword());
//            account.get().setScheduled(false);
//            account.get().setExpired(false);
//            accountRepository.save(account.get());
            //TODO: Is this better?
            accountRepository.updateAccount(accountDTO.getPassword(), accountId);

        }
        catch (Exception e){
            log.error("Error updating password for account={}", accountDTO.getEmail(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Password updated for account_id={}", id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity accountsList() {
        List<Account> accountList = (List<Account>) accountRepository.findAll();
        return new ResponseEntity(mapListToDTO(accountList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity expiredAccounts() {
        List<Account> accountList = accountRepository.findByExpired(Boolean.TRUE);
        return new ResponseEntity(mapListToDTO(accountList), HttpStatus.OK);
    }

    private List<AccountDTO> mapListToDTO(List<Account> accountList){
        List<AccountDTO> accountDTOList = new java.util.ArrayList<>();
        for (Account account: accountList) {
            AccountDTO accountDTO = new AccountDTO(account.getEmail(), account.getPassword(), account.getScheduled());
            accountDTOList.add(accountDTO);
        }
        return accountDTOList;
    }

    private Account parseAndGetAccountById(final String id){
        Long accountId;

        try{
            log.debug("Parsing id={}", id);
            accountId = Long.parseLong(id);
        }
        catch (NumberFormatException e){
            log.warn("Couldn't parse id={}", id);
            return null;
        }

        Optional<Account> account = accountRepository.findById(accountId);

        return account.orElse(null);
    }
}
