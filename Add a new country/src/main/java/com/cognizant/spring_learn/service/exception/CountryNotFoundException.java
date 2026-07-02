package com.cognizant.spring_learn.service.exception;

/**
 * Custom exception thrown when a country is not found by its country code.
 */
public class CountryNotFoundException extends Exception {

    public CountryNotFoundException() {
        super("Country not found");
    }

    public CountryNotFoundException(String message) {
        super(message);
    }

    public CountryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
