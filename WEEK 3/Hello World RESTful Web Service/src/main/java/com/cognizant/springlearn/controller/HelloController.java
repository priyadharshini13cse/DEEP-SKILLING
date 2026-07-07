package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController - REST Controller that handles requests to /hello endpoint.
 * Returns a simple "Hello World!!" greeting string.
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    /**
     * Handles GET requests to /hello.
     *
     * @return hardcoded string "Hello World!!"
     */
    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.info("Start - sayHello()");
        String response = "Hello World!!";
        LOGGER.info("End - sayHello()");
        return response;
    }

}
