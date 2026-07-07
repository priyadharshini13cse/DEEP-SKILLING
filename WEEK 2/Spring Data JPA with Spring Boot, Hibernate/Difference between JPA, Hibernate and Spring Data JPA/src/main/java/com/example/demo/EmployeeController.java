package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private HibernateEmployeeService hibernateEmployeeService;

    @Autowired
    private SpringDataEmployeeService springDataEmployeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employees/hibernate")
    public ResponseEntity<ExecutionTrace> addEmployeeHibernate(@RequestBody Employee employee) {
        try {
            ExecutionTrace trace = hibernateEmployeeService.addEmployee(employee);
            return ResponseEntity.ok(trace);
        } catch (Exception e) {
            ExecutionTrace trace = new ExecutionTrace();
            trace.setStatus("FAILED");
            trace.addLog("[Fatal Error] " + e.getMessage());
            return ResponseEntity.badRequest().body(trace);
        }
    }

    @PostMapping("/employees/spring-data")
    public ResponseEntity<ExecutionTrace> addEmployeeSpringData(@RequestBody Employee employee) {
        try {
            ExecutionTrace trace = springDataEmployeeService.addEmployee(employee);
            return ResponseEntity.ok(trace);
        } catch (Exception e) {
            ExecutionTrace trace = new ExecutionTrace();
            trace.setStatus("FAILED");
            trace.addLog("[Fatal Error] " + e.getMessage());
            return ResponseEntity.badRequest().body(trace);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @DeleteMapping("/employees")
    public ResponseEntity<Void> deleteAllEmployees() {
        employeeRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
