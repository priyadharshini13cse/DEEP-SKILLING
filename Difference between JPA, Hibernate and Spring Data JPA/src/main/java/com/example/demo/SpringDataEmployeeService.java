package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpringDataEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // This method simulates the user's service method. We will execute it inside a wrapper to log details.
    @Transactional
    public Employee saveEmployeeTransactional(Employee employee) {
        return employeeRepository.save(employee);
    }

    public ExecutionTrace addEmployee(Employee employee) {
        ExecutionTrace trace = new ExecutionTrace();
        trace.setCodeSnippet(
            "// --- Spring Data JPA Implementation ---\n" +
            "@Autowired\n" +
            "private EmployeeRepository employeeRepository;\n\n" +
            "@Transactional\n" +
            "public void addEmployee(Employee employee) {\n" +
            "    employeeRepository.save(employee);\n" +
            "}"
        );

        long startTime = System.currentTimeMillis();
        trace.addLog("[Start] Initiating Add Employee via Spring Data JPA...");

        // Step 1: Proxy Interceptor triggers Transaction
        trace.addLog("[Step 1] Spring Transaction Interceptor intercepts call (AOP Proxy)");
        trace.addLog("[Step 2] Automatically opening session & starting transaction behind the scenes");

        try {
            employee.setCreatedVia("Spring Data JPA");
            
            // Step 3: Call simple repository method
            trace.addLog("[Step 3] Calling Repository: employeeRepository.save(employee)");
            trace.addLog("[Step 4] SimpleJpaRepository (Spring Data JPA) translates call to EntityManager.persist()");
            
            Employee saved = saveEmployeeTransactional(employee);
            
            // Step 5: Transaction success
            trace.addLog("[Step 5] Exiting @Transactional method. Transaction Interceptor automatically commits");
            trace.addLog("[Step 6] Session automatically closed and returned to connection pool");

            trace.setEmployee(saved);
            trace.setStatus("SUCCESS");
            trace.setSqlQuery("INSERT INTO employees (name, email, department, salary, created_via) VALUES ('" 
                + saved.getName() + "', '" + saved.getEmail() + "', '" + saved.getDepartment() 
                + "', " + saved.getSalary() + ", 'Spring Data JPA')");
            trace.addLog("[Success] Employee added successfully. ID generated: " + saved.getId());
            
        } catch (Exception e) {
            // Step 6: Proxy handles exception and automatic rollback
            trace.addLog("[Error] Exception occurred! Message: " + e.getMessage());
            trace.addLog("[Rollback] Spring Transaction Interceptor automatically rolls back the transaction");
            trace.setStatus("FAILED");
            throw e;
        }

        long endTime = System.currentTimeMillis();
        trace.setExecutionTimeMs(endTime - startTime);
        trace.addLog("[End] Spring Data JPA addEmployee completed in " + trace.getExecutionTimeMs() + "ms.");
        return trace;
    }
}
