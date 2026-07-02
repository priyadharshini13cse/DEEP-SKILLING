package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin(origins = "*")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public ResponseEntity<List<Country>> searchCountries(@RequestParam(required = false) String name) {
        List<Country> countries = countryService.findCountriesByNameContaining(name);
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Country> getCountryByCode(@PathVariable String code) {
        return countryService.findCountryByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addCountry(@RequestBody Country country) {
        try {
            Country saved = countryService.addCountry(country);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> updateCountry(@PathVariable String code, @RequestBody Country country) {
        try {
            Country updated = countryService.updateCountry(code, country);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteCountry(@PathVariable String code) {
        try {
            countryService.deleteCountry(code);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    public static class ErrorResponse {
        private String message;
        public ErrorResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
