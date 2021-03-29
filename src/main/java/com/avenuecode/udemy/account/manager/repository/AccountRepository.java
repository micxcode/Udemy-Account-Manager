package com.avenuecode.udemy.account.manager.repository;

import com.avenuecode.udemy.account.manager.repository.domain.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query(value = "SELECT account FROM Account account " +
            "WHERE account.scheduled = 'false' and account.expired = 'false' ")
    List<Account> findUnscheduledAccount();

    List<Account> findByExpired(Boolean expired);

    Account findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Account " +
            "SET password = :password," +
            "scheduled = false, " +
            "expired = false " +
            "WHERE id = :accountId")
    void updateAccount(@Param("password") final String password,
                       @Param("accountId") final Long accountId);
}
