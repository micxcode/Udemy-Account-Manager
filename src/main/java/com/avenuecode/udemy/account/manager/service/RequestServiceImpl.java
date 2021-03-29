package com.avenuecode.udemy.account.manager.service;

import com.avenuecode.udemy.account.manager.dto.AccessRequestDTO;
import com.avenuecode.udemy.account.manager.publishers.AccessRequestPublisher;
import com.avenuecode.udemy.account.manager.repository.RequestRepository;
import com.avenuecode.udemy.account.manager.repository.domain.Request;
import com.avenuecode.udemy.account.manager.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService{

    private final RequestRepository requestRepository;
    private final AccessRequestPublisher accessRequestPublisher;

    @Autowired
    public RequestServiceImpl(final RequestRepository requestRepository,
                              final AccessRequestPublisher accessRequestPublisher) {
        this.requestRepository = requestRepository;
        this.accessRequestPublisher = accessRequestPublisher;
    }

    @Override
    public ResponseEntity accessRequest(final AccessRequestDTO accessRequestDTO) {
        log.info("Access Request received for email={}", EmailUtils.maskEmail(accessRequestDTO.getEmail()));

        Request request = new Request(accessRequestDTO.getEmail(), accessRequestDTO.getSeniority(),
                accessRequestDTO.getTechnology(), accessRequestDTO.getHours(), accessRequestDTO.getGoal());

        try{
            log.debug("Saving new request={}", request);
            requestRepository.save(request);
        }
        catch (Exception e){
            log.error("Error saving request from email={} on database.", EmailUtils.maskEmail(accessRequestDTO.getEmail()), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Request created successfully");

        accessRequestPublisher.publish(request);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
