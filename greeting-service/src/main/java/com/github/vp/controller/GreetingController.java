package com.github.vp.controller;

import com.github.vp.model.Greeting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;

/**
 * Created by vimalpar on 15/04/17.
 */
@RestController
public class GreetingController {
    private static final String GREETING_TEMPLATE = "Hello %s! - I am greeting Service - %s!";
    private final AtomicLong counter = new AtomicLong();
    private final String serviceType;

    GreetingController(@Value("${service.type}") String serviceType) {
        this.serviceType = serviceType;
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), format(GREETING_TEMPLATE, name, serviceType));
    }
}
