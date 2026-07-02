package com.cognizant.spring_learn;

import com.cognizant.spring_learn.service.exception.CountryNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for OrmLearnApplication / CountryService.
 *
 * Verifies that findCountryByCode() correctly retrieves a country by code
 * and throws CountryNotFoundException for unknown codes.
 */
@SpringBootTest
class OrmLearnApplicationTests {

    @Autowired
    private CountryService countryService;

    /**
     * Test: find India by country code "IN" and verify the country name.
     */
    @Test
    void testFindCountryByCode_India() throws CountryNotFoundException {
        Country country = countryService.findCountryByCode("IN");

        assertNotNull(country, "Country should not be null");
        assertEquals("IN", country.getCoCode(), "Country code should be IN");
        assertEquals("India", country.getCoName(), "Country name should be India");
    }

    /**
     * Test: find United States by code "US".
     */
    @Test
    void testFindCountryByCode_US() throws CountryNotFoundException {
        Country country = countryService.findCountryByCode("US");

        assertNotNull(country);
        assertEquals("United States", country.getCoName());
    }

    /**
     * Test: CountryNotFoundException is thrown for an unknown country code.
     */
    @Test
    void testFindCountryByCode_NotFound() {
        assertThrows(CountryNotFoundException.class, () ->
                countryService.findCountryByCode("XX"),
                "Should throw CountryNotFoundException for unknown country code"
        );
    }
}
