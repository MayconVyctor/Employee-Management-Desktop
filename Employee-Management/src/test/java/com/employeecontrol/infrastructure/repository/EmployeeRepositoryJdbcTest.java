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
    
}
