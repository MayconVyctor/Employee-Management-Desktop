package com.employeecontrol.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.employeecontrol.domain.entity.Employee;
import com.employeecontrol.domain.repository.EmployeeRepository;

public class RegisterEmployeeUseCase {
    
    private final EmployeeRepository repository;

    public RegisterEmployeeUseCase(EmployeeRepository repository) {
        this.repository = repository;
    }
    
     public Employee execute(String name, LocalDate admissionDate, BigDecimal salary, boolean status) {
         
         Employee employee = new Employee(name, admissionDate, salary, status);
         return repository.save(employee);
     }
}
