package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;

@SpringBootApplication
public class OrmLearnApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(EmployeeService employeeService) {
        return args -> {
            testGetAllPermanentEmployees(employeeService);
        };
    }

    private void testGetAllPermanentEmployees(EmployeeService employeeService) {
        LOGGER.info("Start fetching permanent employees");
        List<Employee> employees = employeeService.getAllPermanentEmployees();
        LOGGER.info("Permanent Employees count: {}", employees.size());
        for (Employee e : employees) {
            LOGGER.info("Employee: {}", e);
            LOGGER.info("Skills: {}", e.getSkillList());
        }
        LOGGER.info("End fetching permanent employees");
    }
}
