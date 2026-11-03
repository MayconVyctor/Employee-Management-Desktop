package com.employeecontrol.application.usecase;

import com.employeecontrol.application.port.PasswordHasher;
import com.employeecontrol.domain.entity.User;
import com.employeecontrol.domain.exception.InvalidCredentialsException;
import com.employeecontrol.domain.repository.UserRepository;

import java.util.Optional;


public class LoginUserUseCase {
    
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;
    
    public LoginUserUseCase(UserRepository repository, PasswordHasher passwordHasher) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
    }
    
    public User execute(String email, String password) {
        
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Email and password cannot be blank");
        }
        
        Optional<User> optionalUser = repository.findByEmail(email);
        
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        
        String passwordHash = passwordHasher.hash(password);

        if (!passwordHash.equals(user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;
         
    }
}
