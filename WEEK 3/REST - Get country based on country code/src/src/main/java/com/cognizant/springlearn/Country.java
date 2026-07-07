package com.cognizant.springlearn;

/**
 * Country - A simple POJO representing a country with a code and name.
 * When returned from a @RestController method, Spring Boot's Jackson
 * auto-configuration serializes it to JSON automatically.
 */
public class Country {

    private String code;
    private String name;

    // Default no-arg constructor (required for Jackson and Spring bean creation)
    public Country() {
    }

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
