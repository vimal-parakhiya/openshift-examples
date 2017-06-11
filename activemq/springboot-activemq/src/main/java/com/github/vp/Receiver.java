package com.github.vp;

/**
 * Created by vimalpar on 10/06/17.
 */
import com.github.vp.config.ConfigProperties;
import com.github.vp.domain.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@ConditionalOnProperty(name = "enable.receiver", havingValue = "true")
@Component
public class Receiver {

    @Autowired
    private ConfigProperties slice;

    @JmsListener(destination = "mailbox", containerFactory = "containerFactory")
    public void receiveMessage(Email email) {
        System.out.println("Receiver Slice: " + slice.getSlice() + " Received <" + email + "> Timestamp: " + new Date());
    }

}
