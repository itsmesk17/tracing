package com.code2learn.opentelemetry.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);


    @GetMapping(path = "/greet/{greetee}")
    public String getPrice(@PathVariable("greetee") String greetee) {
        LOGGER.info("Greeting Service App, Welcomes {}", greetee);
        return "Welcome " + greetee;
    }
}
