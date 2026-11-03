package com.employeecontrol.application.usecase;

import com.employeecontrol.domain.repository.UserRepository;
import com.employeecontrol.application.port.PasswordHasher;
import com.employeecontrol.domain.entity.User;

import com.employeecontrol.domain.exception.UserAlreadyExistsException;

public class RegisterUserUseCase {
    
    private final UserRepository repository;
    private final PasswordHasher passwordHasher;

    
    public RegisterUserUseCase(UserRepository repository, PasswordHasher passwordHasher) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
    }
    
    public User execute(String name, String email, String password) {
        
        if(password == null || password.isBlank()) {
            throw new IllegalArgumentException("password cannot be blank");
        }
        String passwordHash = passwordHasher.hash(password);
        
        User userToCreate = new User(name, email, passwordHash);
        
        boolean emailExists = repository.existsByEmail(email);
        if(emailExists) {
            throw new UserAlreadyExistsException("email already exists");
        }
        
        User userSaved = repository.save(userToCreate);
        return userSaved;
    }
}
