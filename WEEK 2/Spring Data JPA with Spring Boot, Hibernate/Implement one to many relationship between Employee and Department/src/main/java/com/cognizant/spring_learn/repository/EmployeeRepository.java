package com.cognizant.spring_learn.repository;

import com.cognizant.spring_learn.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // Additional query methods can be defined here if needed
}
