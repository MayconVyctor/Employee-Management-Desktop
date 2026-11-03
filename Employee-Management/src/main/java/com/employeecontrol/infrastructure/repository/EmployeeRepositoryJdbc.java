package com.employeecontrol.infrastructure.repository;

import com.employeecontrol.domain.entity.Employee;
import com.employeecontrol.domain.repository.EmployeeRepository;
import com.employeecontrol.infrastructure.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;



public class EmployeeRepositoryJdbc implements EmployeeRepository {
    
    private final Connection connection;
    
    public EmployeeRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public Employee save(Employee employee) {
        String sql = """
                     INSERT INTO employees (name, admission_date, salary, status) 
                     VALUES (?, ?, ?, ?) 
                     RETURNING id, created_at
                     """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setObject(2, employee.getAdmissionDate());
            stmt.setBigDecimal(3, employee.getSalary());
            stmt.setBoolean(4, employee.isStatus());
            
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) {
                    throw new SQLException("Employee insertion did not return generated data.");
                }
            
            return new Employee(result.getLong("id"),
                                  employee.getName(),
                                  employee.getAdmissionDate(),
                                  employee.getSalary(),
                                  employee.isStatus(),
                                  result.getTimestamp("created_at").toInstant());
            }                
        }catch (SQLException err) {
             throw new DatabaseException("Failed to save employee to the database", err);
        }
    }
    
    
}
