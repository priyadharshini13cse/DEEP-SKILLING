package com.cognizant.spring_learn;

import com.cognizant.spring_learn.model.Department;
import com.cognizant.spring_learn.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Spring Boot entry point for the demo.
 * It fetches a Department (with its Employee list) and logs the result.
 */
@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        // invoke the demo method – you can comment/uncomment as needed
        testGetDepartment(context);
        // otherTestMethods(context); // keep other tests commented for now
    }

    /**
     * Demonstrates fetching a department together with its employees.
     * Adjust the id if you load different sample data.
     */
    private static void testGetDepartment(ApplicationContext ctx) {
        DepartmentService departmentService = ctx.getBean(DepartmentService.class);
        Long departmentId = 1L; // the sample data creates a department with id = 1
        Department dept = departmentService.get(departmentId);
        if (dept == null) {
            LOGGER.warn("No department found with id {}", departmentId);
            return;
        }
        LOGGER.info("Fetched Department: {}", dept);
        LOGGER.info("Employee list size: {}", dept.getEmployeeList().size());
        dept.getEmployeeList().forEach(emp -> LOGGER.info("   {}", emp));
    }

    // placeholder for any additional demo methods you might want later
    // private static void otherTestMethods(ApplicationContext ctx) { }
}
