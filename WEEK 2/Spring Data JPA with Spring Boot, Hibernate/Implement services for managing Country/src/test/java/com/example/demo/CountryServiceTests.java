package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CountryServiceTests {

    @Autowired
    private CountryService countryService;

    @Test
    void testFindCountryByCode() {
        // "IN" for India should exist due to data.sql
        Optional<Country> countryOpt = countryService.findCountryByCode("IN");
        assertTrue(countryOpt.isPresent(), "India should be present in the database");
        assertEquals("India", countryOpt.get().getCoName());

        // Test non-existing code
        Optional<Country> noneOpt = countryService.findCountryByCode("XX");
        assertFalse(noneOpt.isPresent(), "XX should not exist in the database");
    }

    @Test
    void testAddCountry() {
        Country newCountry = new Country("XY", "Xanadu Country");
        Country saved = countryService.addCountry(newCountry);
        assertNotNull(saved);
        assertEquals("XY", saved.getCoCode());
        assertEquals("Xanadu Country", saved.getCoName());

        // Verify it was persisted
        Optional<Country> retrieved = countryService.findCountryByCode("XY");
        assertTrue(retrieved.isPresent());
        assertEquals("Xanadu Country", retrieved.get().getCoName());

        // Test duplicate code constraint
        Country duplicate = new Country("XY", "Another Xanadu");
        assertThrows(IllegalArgumentException.class, () -> countryService.addCountry(duplicate),
                "Should fail when trying to add a duplicate country code");
    }

    @Test
    void testUpdateCountry() {
        // "US" for United States should exist
        Optional<Country> usOpt = countryService.findCountryByCode("US");
        assertTrue(usOpt.isPresent());

        Country details = new Country("US", "United States of America");
        Country updated = countryService.updateCountry("US", details);
        assertEquals("United States of America", updated.getCoName());

        // Verify lookup has updated value
        Optional<Country> retrieved = countryService.findCountryByCode("US");
        assertTrue(retrieved.isPresent());
        assertEquals("United States of America", retrieved.get().getCoName());

        // Test updating non-existent country
        Country badDetails = new Country("XX", "No Country");
        assertThrows(IllegalArgumentException.class, () -> countryService.updateCountry("XX", badDetails));
    }

    @Test
    void testDeleteCountry() {
        // Create a temporary country to delete
        Country temp = new Country("ZZ", "Temp Land");
        countryService.addCountry(temp);
        assertTrue(countryService.findCountryByCode("ZZ").isPresent());

        // Delete it
        countryService.deleteCountry("ZZ");

        // Verify deleted
        assertFalse(countryService.findCountryByCode("ZZ").isPresent());

        // Test deleting non-existent country
        assertThrows(IllegalArgumentException.class, () -> countryService.deleteCountry("ZZ"));
    }

    @Test
    void testFindCountriesByNameContaining() {
        // Searching for "United" should match "United Kingdom", "United States", etc.
        List<Country> countries = countryService.findCountriesByNameContaining("United");
        assertFalse(countries.isEmpty(), "Should find countries containing 'United'");
        
        boolean foundUS = countries.stream().anyMatch(c -> c.getCoCode().equals("US"));
        boolean foundGB = countries.stream().anyMatch(c -> c.getCoCode().equals("GB"));
        
        assertTrue(foundUS, "Should contain United States");
        assertTrue(foundGB, "Should contain United Kingdom");

        // Searching case-insensitively
        List<Country> countriesLower = countryService.findCountriesByNameContaining("united");
        assertEquals(countries.size(), countriesLower.size(), "Search should be case-insensitive");

        // Searching for non-existent name
        List<Country> emptyList = countryService.findCountriesByNameContaining("NonExistentCountryName");
        assertTrue(emptyList.isEmpty(), "Should return empty list for unmatched names");
    }
}
