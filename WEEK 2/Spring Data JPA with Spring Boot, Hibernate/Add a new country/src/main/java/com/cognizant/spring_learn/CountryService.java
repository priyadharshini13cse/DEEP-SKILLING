package com.cognizant.spring_learn;

import com.cognizant.spring_learn.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class that handles business logic for Country operations.
 *
 * The @Transactional annotation on findCountryByCode() ensures Spring manages
 * the Hibernate session and transaction lifecycle automatically. This means:
 * - A Hibernate session is opened before the method executes.
 * - The session is committed and closed after the method returns.
 * - If an exception occurs, Spring will roll back the transaction automatically.
 */
@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    /**
     * Finds a country by its country code.
     *
     * @param countryCode the ISO country code (e.g., "IN" for India)
     * @return the Country entity matching the given code
     * @throws CountryNotFoundException if no country exists with the given code
     */
    @Transactional
    public Country findCountryByCode(String countryCode) throws CountryNotFoundException {
        // Use the built-in findById() method from JpaRepository
        Optional<Country> result = countryRepository.findById(countryCode);

        // Check if the country was found; if not, throw CountryNotFoundException
        if (!result.isPresent()) {
            throw new CountryNotFoundException("Country not found for code: " + countryCode);
        }

        // Return the found country using get()
        Country country = result.get();
        return country;
    }

    /**
     * Adds a new country to the database.
     *
     * @param country the Country entity to add
     */
    @Transactional
    public void addCountry(Country country) {
        countryRepository.save(country);
    }
}
