package com.employeecontrol.application.usecase;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.employeecontrol.application.port.PasswordHasher;
import com.employeecontrol.application.usecase.RegisterUserUseCase;
import com.employeecontrol.domain.entity.User;
import com.employeecontrol.domain.exception.UserAlreadyExistsException;
import com.employeecontrol.domain.repository.UserRepository;
import java.time.Instant;


@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private RegisterUserUseCase registerUserUseCase;
    
    @Test
    void shouldRegisterUserWhenEmailDoesNotExist() {
        String password = "password123";
        String passwordHash = "hash_ficticio";

        User savedUser = new User(1L,"Maycon","mike@gmail.com",passwordHash,Instant.now());
         
        when(userRepository.existsByEmail("mike@gmail.com")).thenReturn(false);
        when(passwordHasher.hash(password)).thenReturn(passwordHash);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        User result = registerUserUseCase.execute("Maycon", "mike@gmail.com", password);
        
        assertNotNull(result);
        assertEquals("Maycon", result.getName());
        assertEquals("mike@gmail.com", result.getEmail());
        assertEquals(1L, result.getId());

        verify(userRepository).existsByEmail("mike@gmail.com");
        verify(passwordHasher).hash(password);
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        String password = "password123";
        String passwordHash = "hash_ficticio";
        String email = "mike@gmail.com";

        when(passwordHasher.hash(password)).thenReturn(passwordHash);
        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,
            () -> registerUserUseCase.execute("Maycon", email, password)
        );

        verify(userRepository).existsByEmail(email);
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void shouldThrowExceptionWhenPasswordIsBlank() {

        assertThrows(
            IllegalArgumentException.class,
            () -> registerUserUseCase.execute("Maycon", "mike@gmail.com", " ")
        );

        verifyNoInteractions(passwordHasher, userRepository);
    }
}
