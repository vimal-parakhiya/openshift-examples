package com.github.vp;

/**
 * Created by vimalpar on 10/06/17.
 */

import com.github.vp.config.ConfigProperties;
import com.github.vp.domain.Email;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;

@SpringBootApplication(scanBasePackages = "com.github.vp")
public class Application {
    public static void main(String[] args) throws InterruptedException {
        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        ConfigProperties configProperties = context.getBean(ConfigProperties.class);
        System.out.println(configProperties);

        if(configProperties.isEnableSender()) {
            int index = 0;
            while (true) {
                sendMessage(jmsTemplate, configProperties, index);
                index++;
                Thread.sleep(configProperties.getDelayBetweenMessages());
            }
        }
    }

    private static void sendMessage(JmsTemplate jmsTemplate,  ConfigProperties configProperties, int index) throws InterruptedException {
        boolean messageSent=false;
        int retries=0;
        while(!messageSent && retries < configProperties.getMaxRetries()) {
            String message = String.format("Hello - Slice %s, Index %s, Retry %s, Timestamp: %s", configProperties.getSlice(), index, retries, new Date());
            try {
                System.out.println("Sending Message - " + message);
                jmsTemplate.convertAndSend("mailbox", new Email("info@example.com", message));
                messageSent=true;
            } catch (Exception ex) {
                System.out.println("Failed to send message: " + message + " Error: " + ex.getMessage());
                Thread.sleep(configProperties.getRetryDelay());
                retries++;
            }
        }
    }
}
