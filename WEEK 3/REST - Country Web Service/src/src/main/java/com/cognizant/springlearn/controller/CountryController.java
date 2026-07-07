package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.Country;

/**
 * CountryController - REST Controller that handles requests to /country endpoint.
 * Loads the India bean from the Spring XML configuration and returns it as JSON.
 */
@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    @Qualifier("india")
    private Country india;

    /**
     * Handles GET requests to /country.
     * Loads the India bean from the Spring XML configuration and returns it.
     * Spring Boot automatically converts the Country object to JSON using Jackson.
     *
     * @return Country object for India (serialized as JSON)
     */
    @RequestMapping("/country")
    public Country getCountryIndia() {
        LOGGER.info("Start - getCountryIndia()");
        LOGGER.info("Country code: {}, name: {}", india.getCode(), india.getName());
        LOGGER.info("End - getCountryIndia()");
        return india;
    }

}
