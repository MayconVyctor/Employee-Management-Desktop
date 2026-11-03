
package com.employeecontrol.application.usecase;

import com.employeecontrol.domain.repository.EmployeeRepository;
import com.employeecontrol.domain.entity.Employee;

import java.util.List;


public class ListEmployeesUseCase {
    
    private final EmployeeRepository repository;
    
    public ListEmployeesUseCase(EmployeeRepository repository) {
        this.repository = repository;
    }
    
    public List<Employee> execute() {
        return repository.findAll();
    }
}
