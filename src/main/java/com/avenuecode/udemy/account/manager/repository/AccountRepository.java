package com.avenuecode.udemy.account.manager.repository;

import com.avenuecode.udemy.account.manager.repository.domain.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query(value = "SELECT account FROM Account account " +
            "WHERE account.scheduled = 'false' and account.expired = 'false' ")
    List<Account> findUnscheduledAccount();

    List<Account> findByExpired(Boolean expired);

    Account findByEmail(String email);
}
