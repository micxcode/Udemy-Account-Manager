package com.avenuecode.udemy.account.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = { "com.avenuecode.udemy.account.manager.publishers" })
public class ScheduleConfiguration implements SchedulingConfigurer {

    public static final String SHUTDOWN = "shutdown";

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod= SHUTDOWN)
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }
}
