package com.employeecontrol.domain.repository;

import com.employeecontrol.domain.entity.Employee;
import java.util.List;

public interface EmployeeRepository {
    
   Employee save(Employee employee);
   
   List<Employee> findAll();
}
