package com.cognizant.springlearn.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.cognizant.springlearn.Country;

/**
 * CountryService - Service class that loads country data from country.xml
 * and provides lookup functionality by country code (case-insensitive).
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    /**
     * Retrieves a country by its code (case-insensitive) from country.xml.
     *
     * Loads the country list from the XML file, then uses a lambda expression
     * (stream + filter) to find the matching country.
     *
     * @param code the country code to search for (e.g., "in", "IN", "In")
     * @return the matching Country object, or null if not found
     */
    public Country getCountry(String code) {
        LOGGER.info("Start - getCountry({})", code);

        List<Country> countryList = getCountryList();

        // Using lambda expression with stream to find country by code (case-insensitive)
        Country country = countryList.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);

        if (country != null) {
            LOGGER.info("Country found - code: {}, name: {}", country.getCode(), country.getName());
        } else {
            LOGGER.warn("No country found for code: {}", code);
        }

        LOGGER.info("End - getCountry({})", code);
        return country;
    }

    /**
     * Loads the list of countries from country.xml on the classpath.
     *
     * @return List of Country objects parsed from the XML file
     */
    private List<Country> getCountryList() {
        List<Country> countryList = new ArrayList<>();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("country.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("country");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String code = element.getElementsByTagName("code").item(0).getTextContent();
                String name = element.getElementsByTagName("name").item(0).getTextContent();

                Country country = new Country(code, name);
                countryList.add(country);
            }

            LOGGER.info("Loaded {} countries from country.xml", countryList.size());

        } catch (Exception e) {
            LOGGER.error("Error loading country.xml", e);
        }

        return countryList;
    }
}
