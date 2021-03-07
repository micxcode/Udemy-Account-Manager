package com.avenuecode.udemy.account.manager.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
public class JmsErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
    }
}
