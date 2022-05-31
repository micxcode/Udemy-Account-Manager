package com.avenuecode.udemy.account.manager.repository;

import com.avenuecode.udemy.account.manager.repository.domain.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
    List<Request> findByScheduled(Boolean scheduled);
}
