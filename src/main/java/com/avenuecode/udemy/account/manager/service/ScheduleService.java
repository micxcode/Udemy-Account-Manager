package com.avenuecode.udemy.account.manager.service;

import com.avenuecode.udemy.account.manager.dto.AccessRequestIdDTO;
import com.avenuecode.udemy.account.manager.dto.ScheduleIdDTO;
import com.avenuecode.udemy.account.manager.repository.AccountRepository;
import com.avenuecode.udemy.account.manager.repository.RequestRepository;
import com.avenuecode.udemy.account.manager.repository.ScheduleRepository;
import com.avenuecode.udemy.account.manager.repository.domain.Account;
import com.avenuecode.udemy.account.manager.repository.domain.Request;
import com.avenuecode.udemy.account.manager.repository.domain.Schedule;
import com.avenuecode.udemy.account.manager.utils.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AccountRepository accountRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public ScheduleService(final ScheduleRepository scheduleRepository, final AccountRepository accountRepository,
                           final RequestRepository requestRepository) {
        this.scheduleRepository = scheduleRepository;
        this.accountRepository = accountRepository;
        this.requestRepository = requestRepository;
    }

    public void createSchedule(final AccessRequestIdDTO accessRequestIdDTO){
        log.debug("Received access request with id={}", accessRequestIdDTO.getId());
        //Find request
        Optional<Request> savedRequest = requestRepository.findById(accessRequestIdDTO.getId());

        if(!savedRequest.isPresent()){
            throw new IllegalArgumentException(
                    String.format("Could not find request of id '%s'!", accessRequestIdDTO.getId()));
        }

        Request request = savedRequest.get();
        log.debug("Request found on database");

        if(Boolean.TRUE.equals(request.getScheduled())){
            log.info("Request of id={} is already scheduled", accessRequestIdDTO.getId());
            return;
        }

        //Find available accounts
        List<Account> accounts = accountRepository.findUnscheduledAccount();
        if(accounts.isEmpty()){
            log.info("No accounts available to attend request from email={}", EmailUtils.maskEmail(request.getEmail()));
            return;
        }

        //Gets the first account available
        Account account = accounts.get(0);

        //TODO: Include endDate logic
        Date endDate = new Date();

        Schedule schedule = new Schedule(account, endDate, request.getEmail(),
                request.getSeniority(), request.getTechnology(), request.getHours(), request.getGoal());

        log.info("Creating schedule for email={}, with account={}", EmailUtils.maskEmail(request.getEmail()), account.getEmail());

        try{
            //Mark account and request as scheduled
            scheduleRepository.save(schedule);
            account.setScheduled(true);
            account.getSchedules().add(schedule);
            accountRepository.save(account);
            request.setScheduled(true);
            requestRepository.save(request);
        }
        catch (Exception e){
            log.error("Error scheduling account={} for email={}", account.getEmail(), EmailUtils.maskEmail(request.getEmail()), e);
            throw e;
        }

        //TODO: Include logs

        //TODO: Notify user that his account is ready to use
    }

    public void checkSchedule(final ScheduleIdDTO scheduleIdDTO){
        //Find schedule
        Optional<Schedule> savedSchedule = scheduleRepository.findById(scheduleIdDTO.getId());

        if(!savedSchedule.isPresent()){
            throw new IllegalArgumentException(
                    String.format("Could not find schedule of id '%s'!", scheduleIdDTO.getId()));
        }

        Schedule schedule = savedSchedule.get();

        if(Boolean.FALSE.equals(schedule.getActive())){
            log.info("Schedule of id={} is no longer active", scheduleIdDTO.getId());
            return;
        }

        //Check end date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if(sdf.format(new Date()).equals(sdf.format(schedule.getEnd()))){
            log.info("Schedule of id={} has expired", schedule.getId());

            try{
                schedule.setActive(false);
                Account account = schedule.getAccount();
                account.setExpired(true);
                accountRepository.save(account);
                scheduleRepository.save(schedule);
            }
            catch (Exception e){
                log.error("Error deactivating schedule of id={} for account={}", schedule.getId(), schedule.getAccount().getEmail(), e);
                throw e;
            }
            //TODO: Include logs

            //TODO: Notify users their access has expired

            //TODO: Notify account managers so they can reset the password
        }


    }
}
