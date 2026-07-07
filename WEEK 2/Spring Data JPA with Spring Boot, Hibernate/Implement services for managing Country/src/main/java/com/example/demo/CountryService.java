package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Optional<Country> findCountryByCode(String code) {
        if (code == null) {
            return Optional.empty();
        }
        return countryRepository.findById(code.trim().toUpperCase());
    }

    @Transactional
    public Country addCountry(Country country) {
        if (country == null || country.getCoCode() == null || country.getCoCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Country code cannot be empty");
        }
        if (country.getCoName() == null || country.getCoName().trim().isEmpty()) {
            throw new IllegalArgumentException("Country name cannot be empty");
        }
        String code = country.getCoCode().trim().toUpperCase();
        if (countryRepository.existsById(code)) {
            throw new IllegalArgumentException("Country with code " + code + " already exists.");
        }
        country.setCoCode(code);
        return countryRepository.save(country);
    }

    @Transactional
    public Country updateCountry(String code, Country countryDetails) {
        if (code == null || countryDetails == null) {
            throw new IllegalArgumentException("Invalid input data");
        }
        if (countryDetails.getCoName() == null || countryDetails.getCoName().trim().isEmpty()) {
            throw new IllegalArgumentException("Country name cannot be empty");
        }
        String searchCode = code.trim().toUpperCase();
        Country country = countryRepository.findById(searchCode)
                .orElseThrow(() -> new IllegalArgumentException("Country not found with code: " + searchCode));

        country.setCoName(countryDetails.getCoName().trim());
        return countryRepository.save(country);
    }

    @Transactional
    public void deleteCountry(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Country code cannot be null");
        }
        String searchCode = code.trim().toUpperCase();
        if (!countryRepository.existsById(searchCode)) {
            throw new IllegalArgumentException("Country not found with code: " + searchCode);
        }
        countryRepository.deleteById(searchCode);
    }

    public List<Country> findCountriesByNameContaining(String name) {
        if (name == null || name.trim().isEmpty()) {
            return countryRepository.findAll();
        }
        return countryRepository.findByCoNameContainingIgnoreCase(name.trim());
    }
}
