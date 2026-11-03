package com.employeecontrol.application.usecase;

import static org.mockito.Mockito.*;

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

import com.employeecontrol.domain.repository.UserRepository;
import com.employeecontrol.domain.entity.User;
import java.time.Instant;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LoginUserUseCaseTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private LoginUserUseCase loginUserUseCase;
    
    @Test
    void shouldReturnUserWhenCredentialsAreValid() {
        String password = "password123";
        String passwordHash = "hash_ficticio";
        String email = "mike@gmail.com";
        
        User existingUser = new User(1L,"Maycon","mike@gmail.com",passwordHash,Instant.now());

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));
        when(passwordHasher.hash(password)).thenReturn(passwordHash);
        
        User result = loginUserUseCase.execute(email, password);
        
        assertNotNull(result);
        assertEquals(existingUser.getId(), result.getId());
        assertEquals(existingUser.getEmail(), result.getEmail());

        verify(userRepository).findByEmail(email);
        verify(passwordHasher).hash(password);
    }
}
