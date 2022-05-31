package com.avenuecode.udemy.account.manager.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfiguration {

    @Value("${avenue-code.udemy.jms.broker-url}")
    private String brokerUrl;

    @Value("${avenue-code.udemy.jms.max-connections}")
    private int maxConnections;

    @Value("${avenue-code.udemy.jms.idle-timeout}")
    private int idleTimeout;

    @Value("${avenue-code.udemy.jms.factory.concurrency:5}")
    private String concurrency;

    private final ErrorHandler jmsErrorHandler;

    public JmsConfiguration(final ErrorHandler jmsErrorHandler) {
        this.jmsErrorHandler = jmsErrorHandler;
    }

    @Bean("udemyJmsTemplate")
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        return jmsTemplate;
    }

    @Bean("udemyJmsListener")
    public JmsListenerContainerFactory<DefaultMessageListenerContainer> jmsFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory());

        // enable transacted dequeue
        factory.setSessionTransacted(true);

        factory.setErrorHandler(jmsErrorHandler);

        // disable two-phase-commit
        factory.setTransactionManager(null);

        factory.setMessageConverter(jacksonJmsMessageConverter());

        factory.setConcurrency(concurrency);

        return factory;
    }

    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMqConnectionFactory = new ActiveMQConnectionFactory();
        activeMqConnectionFactory.setBrokerURL(brokerUrl);

        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(true);
        activeMqConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        JmsPoolConnectionFactory connectionFactory = new JmsPoolConnectionFactory();
        connectionFactory.setConnectionFactory(activeMqConnectionFactory);
        connectionFactory.setConnectionIdleTimeout(idleTimeout);
        connectionFactory.setMaxConnections(maxConnections);

        return connectionFactory;
    }

    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTypeIdPropertyName("_type");
        converter.setTargetType(MessageType.TEXT);
        return converter;
    }
}
