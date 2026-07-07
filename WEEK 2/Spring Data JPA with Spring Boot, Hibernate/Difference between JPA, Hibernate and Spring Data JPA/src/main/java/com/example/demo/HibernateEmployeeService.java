package com.example.demo;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HibernateEmployeeService {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public ExecutionTrace addEmployee(Employee employee) {
        ExecutionTrace trace = new ExecutionTrace();
        trace.setCodeSnippet(
            "// --- Hibernate Native Implementation ---\n" +
            "public Integer addEmployee(Employee employee) {\n" +
            "    Session session = factory.openSession();\n" +
            "    Transaction tx = null;\n" +
            "    try {\n" +
            "        tx = session.beginTransaction();\n" +
            "        session.persist(employee); // Hibernate 6 uses persist instead of save\n" +
            "        tx.commit();\n" +
            "    } catch (Exception e) {\n" +
            "        if (tx != null) tx.rollback();\n" +
            "        throw e;\n" +
            "    } finally {\n" +
            "        session.close();\n" +
            "    }\n" +
            "    return employee.getId();\n" +
            "}"
        );

        long startTime = System.currentTimeMillis();
        trace.addLog("[Start] Initiating Add Employee via native Hibernate...");

        // Step 1: Unwrap EntityManagerFactory to Hibernate SessionFactory
        trace.addLog("[Step 1] Unwrapping JPA EntityManagerFactory to Hibernate SessionFactory");
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            // Step 2: Open a new Hibernate Session
            trace.addLog("[Step 2] Opening Session: Session session = sessionFactory.openSession()");
            session = sessionFactory.openSession();
            
            // Step 3: Begin a transaction manually
            trace.addLog("[Step 3] Beginning Transaction: Transaction tx = session.beginTransaction()");
            tx = session.beginTransaction();
            
            // Step 4: Perform the save operation
            trace.addLog("[Step 4] Persisting Employee entity: session.persist(employee)");
            employee.setCreatedVia("Hibernate");
            session.persist(employee);
            
            // Step 5: Commit the transaction manually
            trace.addLog("[Step 5] Committing Transaction: tx.commit()");
            tx.commit();
            
            trace.setEmployee(employee);
            trace.setStatus("SUCCESS");
            trace.setSqlQuery("INSERT INTO employees (name, email, department, salary, created_via) VALUES ('" 
                + employee.getName() + "', '" + employee.getEmail() + "', '" + employee.getDepartment() 
                + "', " + employee.getSalary() + ", 'Hibernate')");
            trace.addLog("[Success] Employee added successfully. ID generated: " + employee.getId());
            
        } catch (Exception e) {
            // Step 6: Handle exceptions and rollback transaction manually
            trace.addLog("[Error] Hibernate Exception occurred! Message: " + e.getMessage());
            if (tx != null && tx.isActive()) {
                trace.addLog("[Rollback] Transaction active. Rolling back changes: tx.rollback()");
                try {
                    tx.rollback();
                } catch (Exception ex) {
                    trace.addLog("[Error] Failed to rollback transaction: " + ex.getMessage());
                }
            }
            trace.setStatus("FAILED");
            throw e;
        } finally {
            // Step 7: Close the session manually in the finally block
            if (session != null && session.isOpen()) {
                trace.addLog("[Step 6/7] Closing Session manually in finally block: session.close()");
                session.close();
            }
        }
        
        long endTime = System.currentTimeMillis();
        trace.setExecutionTimeMs(endTime - startTime);
        trace.addLog("[End] Hibernate addEmployee completed in " + trace.getExecutionTimeMs() + "ms.");
        return trace;
    }
}
