package com.cognizant.spring_learn.repository;

import com.cognizant.spring_learn.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // Additional query methods if needed
}
