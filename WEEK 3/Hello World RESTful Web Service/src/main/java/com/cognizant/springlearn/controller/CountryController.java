package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.CountryService;

/**
 * CountryController - REST Controller that handles country-related endpoints.
 * Provides endpoints to get a specific country by code (case-insensitive)
 * and to get the India country bean from XML configuration.
 */
@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    @Qualifier("india")
    private Country india;

    @Autowired
    private CountryService countryService;

    /**
     * Handles GET requests to /countries/{code}.
     * Retrieves a country by its code (case-insensitive) from country.xml
     * by delegating to CountryService.
     *
     * @param code the country code (e.g., "in", "IN", "In")
     * @return Country object matching the code (serialized as JSON)
     */
    @GetMapping("/countries/{code}")
    public Country getCountry(@PathVariable String code) {
        LOGGER.info("Start - getCountry({})", code);
        Country country = countryService.getCountry(code);
        LOGGER.info("End - getCountry({})", code);
        return country;
    }

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

