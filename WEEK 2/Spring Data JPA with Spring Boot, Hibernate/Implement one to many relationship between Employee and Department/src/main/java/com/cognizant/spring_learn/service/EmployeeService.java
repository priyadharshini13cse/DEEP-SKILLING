package com.cognizant.spring_learn.service;

import com.cognizant.spring_learn.model.Employee;
import com.cognizant.spring_learn.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Employee get(int id) {
        LOGGER.info("Start get Employee id={}", id);
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Employee employee) {
        LOGGER.info("Start save Employee");
        employeeRepository.save(employee);
        LOGGER.info("End save Employee");
    }
}
