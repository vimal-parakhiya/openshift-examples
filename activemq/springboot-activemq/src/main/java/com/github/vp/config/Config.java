package com.github.vp.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

/**
 * Created by vimalpar on 10/06/17.
 */
@Configuration
@EnableJms
public class Config {
    @Bean
    public JmsListenerContainerFactory<?> containerFactory(ConnectionFactory connectionFactory,
                                                           DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.activemq")
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory();
    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public ConfigProperties configProperties(@Value("${app.slice}") String appSlice,
                                             @Value("${enable.sender}") boolean enableSender,
                                             @Value("${enable.receiver}") boolean enableReceiver,
                                             @Value("${sender.delay.between.messages}") int delayBetweenMessages,
                                             @Value("${sender.retry.delay}") int retryDelay,
                                             @Value("${sender.max.retry.count}") int maxRetries) {
        return new ConfigProperties(appSlice, enableSender, enableReceiver, delayBetweenMessages, retryDelay, maxRetries);
    }
}
