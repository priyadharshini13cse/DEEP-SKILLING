package com.cognizant.spring_learn.service;

import com.cognizant.spring_learn.model.Department;
import com.cognizant.spring_learn.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public Department get(Long id) {
        LOGGER.info("Start get Department id={}", id);
        return departmentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Department department) {
        LOGGER.info("Start save Department");
        departmentRepository.save(department);
        LOGGER.info("End save Department");
    }
}
