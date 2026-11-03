
package com.employeecontrol.domain.entity;

import java.time.LocalDate;
import java.time.Instant;
import java.math.BigDecimal;


public class Employee {
    
    private final Long id;
    private final String name;
    private final LocalDate admissionDate;
    private final BigDecimal salary;
    private final boolean status;
    private final Instant createdAt;
    
    public Employee(Long id, String name, LocalDate admissionDate, BigDecimal salary, boolean status, Instant createdAt) {
        validateIdAndCreatedAt(id, createdAt);
        this.id = id;
        this.name = validateNoBlank(name, "name");
        this.admissionDate = validateAdmissionDate(admissionDate); 
        this.salary = validateSalary(salary);
        this.status = status;
        this.createdAt = createdAt;
    }
    
    public Employee(String name, LocalDate admissionDate, BigDecimal salary) {
        this(null, name, admissionDate, salary, true, null);
    }
    
    public Employee(String name, LocalDate admissionDate, BigDecimal salary, boolean status) {
    this(null, name, admissionDate, salary, status, null);
}
    
    private static void validateIdAndCreatedAt(Long id, Instant createdAt) {
        if (id != null && id <= 0) {
            throw new IllegalArgumentException("The id must be greater than zero");
        }
        
        boolean newEmployee = id == null && createdAt == null;
        boolean persistedEmployee = id != null && createdAt != null;
        if (!newEmployee  && !persistedEmployee) {
            throw new IllegalArgumentException(
                "id and createdAt must both be null (new employee) or both be set (persisted employee)");
        }
    }
     
    private static String validateNoBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("The " + field + " is required");
        }
        return value.trim();
    }
    
    private static LocalDate validateAdmissionDate(LocalDate date) {
      if (date == null) {
          throw new IllegalArgumentException("Admission date is required");
      }
      return date;
    }
    
    private static BigDecimal validateSalary(BigDecimal salary) {
        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The salary must be greater than zero");
        }
        return salary;
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }
    
    public boolean isStatus() {
        return status;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
