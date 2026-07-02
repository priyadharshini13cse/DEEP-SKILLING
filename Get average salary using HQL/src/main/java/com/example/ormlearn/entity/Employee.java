package com.example.ormlearn.entity;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
 private Department department;

 // getters and setters
 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }
 public double getSalary() { return salary; }
 public void setSalary(double salary) { this.salary = salary; }
 public Department getDepartment() { return department; }
 public void setDepartment(Department department) { this.department = department; }
}
