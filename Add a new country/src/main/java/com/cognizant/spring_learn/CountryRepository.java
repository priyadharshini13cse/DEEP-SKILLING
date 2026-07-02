package com.cognizant.spring_learn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Country entity.
 * Extends JpaRepository providing built-in CRUD operations including findById().
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
}
