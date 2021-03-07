package com.avenuecode.udemy.account.manager.repository;

import com.avenuecode.udemy.account.manager.repository.domain.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Long> {
    List<Request> findByScheduled(Boolean scheduled);
}
