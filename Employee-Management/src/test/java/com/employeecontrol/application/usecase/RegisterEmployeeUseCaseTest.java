package com.employeecontrol.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.employeecontrol.domain.entity.Employee;
import com.employeecontrol.domain.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class RegisterEmployeeUseCaseTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private RegisterEmployeeUseCase registerEmployeeUseCase;

    @Test
    void shouldRegisterEmployeeWhenDataIsValid() {
        String name = "Maycon";
        LocalDate admissionDate = LocalDate.of(2026, 11, 15);
        BigDecimal salary = new BigDecimal("3500.00");
        boolean status = true;

        Employee savedEmployee = new Employee(1L, name, admissionDate, salary, status, Instant.now());

        when(employeeRepository.save(any(Employee.class)))
            .thenReturn(savedEmployee);

        Employee result = registerEmployeeUseCase.execute(
            name, admissionDate, salary, status
        );

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(name, result.getName());
        assertEquals(admissionDate, result.getAdmissionDate());
        assertEquals(0, salary.compareTo(result.getSalary()));
        assertEquals(status, result.isStatus());

        verify(employeeRepository).save(any(Employee.class));
    }
}