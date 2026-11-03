package com.employeecontrol.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.employeecontrol.domain.entity.Employee;
import com.employeecontrol.domain.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class ListEmployeesUseCaseTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ListEmployeesUseCase listEmployeesUseCase;

    @Test
    void shouldReturnAllEmployeesWhenEmployeesExist() {
        Employee employee = new Employee(1L, "Maycon", LocalDate.of(2026, 10, 15), new BigDecimal("3500.00"), true, Instant.now());

        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<Employee> result = listEmployeesUseCase.execute();

        assertEquals(1, result.size());
        assertEquals(employee, result.get(0));

        verify(employeeRepository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoEmployeesExist() {
        when(employeeRepository.findAll()).thenReturn(List.of());

        List<Employee> result = listEmployeesUseCase.execute();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(employeeRepository).findAll();
    }
}