package com.example.ormlearn;

import com.example.ormlearn.entity.Employee;
import com.example.ormlearn.service.EmployeeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrmLearnApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);
    private final EmployeeService employeeService;

    public OrmLearnApplication(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Starting testGetAllEmployeesNative...");
        testGetAllEmployeesNative();
        LOGGER.info("Finished testGetAllEmployeesNative.");
    }

    private void testGetAllEmployeesNative() {
        List<Employee> employees = employeeService.getAllEmployeesNative();
        LOGGER.info("Employees fetched using Native Query:");
        for (Employee employee : employees) {
            LOGGER.info("Employee: id={}, name={}, salary={}", 
                employee.getId(), employee.getName(), employee.getSalary());
        }
    }
}
