package com.avenuecode.udemy.account.manager.service;

import com.avenuecode.udemy.account.manager.enums.UserTemplate;
import com.avenuecode.udemy.account.manager.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailingService {

    public static void notifyUser(String email, UserTemplate template){
        log.info("Notifying user={} with template={}", EmailUtils.maskEmail(email), template.name());
    }

    public static void notifyAccountManagers(String account){
        log.info("Notifying account managers");
    }
}
