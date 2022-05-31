package com.avenuecode.udemy.account.manager.service;

import com.avenuecode.udemy.account.manager.dto.AccessRequestDTO;
import org.springframework.http.ResponseEntity;

public interface RequestService {
    ResponseEntity accessRequest (final AccessRequestDTO accessRequestDTO);
}
