package com.cognizant.spring_learn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing the 'country' table.
 */
@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "co_code", length = 10)
    private String coCode;

    @Column(name = "co_name", nullable = false)
    private String coName;

    public Country() {
    }

    public Country(String coCode, String coName) {
        this.coCode = coCode;
        this.coName = coName;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    @Override
    public String toString() {
        return "Country{coCode='" + coCode + "', coName='" + coName + "'}";
    }
}
