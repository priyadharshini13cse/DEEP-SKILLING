package com.cognizant.spring_learn;

import com.cognizant.spring_learn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Main Spring Boot application class for the ORM learning module.
 *
 * Demonstrates finding a country by country code using Spring Data JPA
 * with @Transactional service methods.
 */
@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;

    public static void main(String[] args) throws CountryNotFoundException {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);

        // Retrieve the CountryService bean from the Spring application context
        countryService = context.getBean(CountryService.class);

        // Invoke the test method to find a country by code
        getCountryByCodeTest();
    }

    /**
     * Test method that retrieves a country based on country code ("IN" = India)
     * and logs the result. The method name follows the convention used in the lab.
     *
     * Note: The lab instructions show "getAllCountriesTest()" as the method name
     * but the actual logic performs a single-country lookup by code.
     */
    private static void getCountryByCodeTest() throws CountryNotFoundException {
        LOGGER.info("Start");

        Country country = countryService.findCountryByCode("IN");

        // Log at DEBUG level (visible when logging.level.com.cognizant=DEBUG)
        LOGGER.debug("Country:{}", country);

        // Validate that the returned country name matches the expected value
        assert "India".equals(country.getCoName()) : "Expected 'India' but got: " + country.getCoName();

        LOGGER.info("End");
    }
}
