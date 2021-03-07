package com.avenuecode.udemy.account.manager.publishers;

import com.avenuecode.udemy.account.manager.repository.RequestRepository;
import com.avenuecode.udemy.account.manager.repository.domain.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(
        value = "avenue-code.udemy.jms.accessRequest.scheduler.enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableAsync(proxyTargetClass = true)
@Slf4j
public class AccessRequestPublisherScheduler implements Runnable {

    private final RequestRepository requestRepository;
    private final AccessRequestPublisher accessRequestPublisher;

    public AccessRequestPublisherScheduler(final RequestRepository requestRepository,
                                           final AccessRequestPublisher accessRequestPublisher) {
        this.requestRepository = requestRepository;
        this.accessRequestPublisher = accessRequestPublisher;
        log.info("Initializing AccessRequestPublisherScheduler...");
    }

    @Override
    @Async
    @Scheduled(fixedRateString = "${avenue-code.udemy.jms.accessRequest.pollingInterval}")
    public void run() {
        List<Request> requests = requestRepository.findByScheduled(Boolean.FALSE);
        requests.forEach(accessRequestPublisher::publish);
    }
}
