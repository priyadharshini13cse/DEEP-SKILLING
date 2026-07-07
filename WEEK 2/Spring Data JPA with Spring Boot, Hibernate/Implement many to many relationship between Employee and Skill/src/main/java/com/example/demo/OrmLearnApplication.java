package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Skill;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrmLearnApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(EmployeeService employeeService, SkillService skillService) {
        return args -> {
            // Uncomment one of the test methods to run
            // testGetEmployee(employeeService);
            // testAddSkillToEmployee(employeeService, skillService);
        };
    }

    private void testGetEmployee(EmployeeService employeeService) {
        // Adjust the ID to an existing employee in your database
        Employee employee = employeeService.get(1L);
        LOGGER.debug("Employee: {}", employee.getName());
        // Assuming you have a department field, replace or remove as needed
        // LOGGER.debug("Department: {}", employee.getDepartment());
        LOGGER.debug("Skills: {}", employee.getSkillList());
    }

    private void testAddSkillToEmployee(EmployeeService employeeService, SkillService skillService) {
        // Choose IDs that currently do NOT have a relationship
        Long employeeId = 1L;
        Long skillId = 2L;
        Employee employee = employeeService.get(employeeId);
        Skill skill = skillService.get(skillId);
        employee.getSkillList().add(skill);
        employeeService.save(employee);
        LOGGER.info("Added skill '{}' to employee '{}'", skill.getName(), employee.getName());
    }
}
