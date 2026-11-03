package com.employeecontrol.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import com.employeecontrol.infrastructure.database.DatabaseConnection;
import com.employeecontrol.domain.entity.Employee;

import java.sql.Connection;
import java.time.LocalDate;
import java.math.BigDecimal;

import java.util.List;

public class EmployeeRepositoryJdbcTest {
    
    private Connection connection;
    private EmployeeRepositoryJdbc repository;
    
    @BeforeEach
    void setUp() {
        connection = DatabaseConnection.getConnection();
        repository = new EmployeeRepositoryJdbc(connection);
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }
    
    @Test
    void shouldReturnPersistedEmployeeWhenSavingValidEmployee() {
        LocalDate admissionDate = LocalDate.of(2026, 9, 11);

        Employee employeeToCreate = new Employee("Maycon", admissionDate, new BigDecimal("3500.00"));
        
        Employee savedEmployee = repository.save(employeeToCreate);
        
        assertNotNull(savedEmployee.getId());
        assertTrue(savedEmployee.getId() > 0);
        assertNotNull(savedEmployee.getCreatedAt());
        assertEquals("Maycon", savedEmployee.getName());
        assertEquals(admissionDate, savedEmployee.getAdmissionDate());
        assertEquals(new BigDecimal("3500.00"), savedEmployee.getSalary());
        assertTrue(savedEmployee.isStatus());
    }
    
    @Test
    void shouldReturnAllEmployeesWhenEmployeesExist() {
        LocalDate admissionDate1 = LocalDate.of(2026, 11, 16);
        LocalDate admissionDate2 = LocalDate.of(2026, 11, 23);

        Employee employeeToCreate = new Employee("Maycon Vyctor", admissionDate1, new BigDecimal("3500.00"));
        Employee employeeToCreate2 = new Employee("Mike", admissionDate2, new BigDecimal("3500.00"));

        
        Employee savedEmployee = repository.save(employeeToCreate);
        Employee savedEmployee2 = repository.save(employeeToCreate2);
        
        List<Employee> employees = repository.findAll();
        
        boolean employee1Exists = employees.stream()
                                    .anyMatch(employee ->   
                                    employee.getId().equals(savedEmployee.getId()));
        
        assertTrue(employee1Exists);
        
        boolean employee2Exists = employees.stream()
                                    .anyMatch(employee ->   
                                    employee.getId().equals(savedEmployee2.getId()));
        
        assertTrue(employee2Exists);
    }
    
    @Test
    void shouldReturnEmptyListWhenNoEmployeesExist() {
        
        List<Employee> employees = repository.findAll();
        
        assertTrue(employees.isEmpty());
    }
}
