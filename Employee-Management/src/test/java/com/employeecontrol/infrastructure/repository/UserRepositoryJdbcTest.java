package com.employeecontrol.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Optional;

import java.sql.DriverManager;
import com.employeecontrol.domain.entity.User;

import java.sql.Connection;


public class UserRepositoryJdbcTest {
    
    private Connection connection;
    private UserRepositoryJdbc repository;
    
    @BeforeEach
    void setUp() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/employee_control_test";

        connection = DriverManager.getConnection(
            url,
            "postgres",
            "postgrespassword"
        );

        connection.setAutoCommit(false);

        repository = new UserRepositoryJdbc(connection);
    }
    
    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            try {
                connection.rollback();
            } finally {
                connection.close();
            }
        }
    }
    
    @Test
    void shouldReturnPersistedUserWhenSavingValidUser() {
        User userToCreate = new User("Maycon", "mike@gmail.com", "password123");
        
        User savedUser = repository.save(userToCreate);
        
        assertNotNull(savedUser.getId());
        assertTrue(savedUser.getId() > 0);
        assertNotNull(savedUser.getCreatedAt());
        assertEquals("Maycon", savedUser.getName());
        assertEquals("mike@gmail.com", savedUser.getEmail());
        assertEquals("password123", savedUser.getPasswordHash());   
    }
    
    @Test
    void shouldReturnUserWhenEmailExists() {
        User userToCreate = new User("Maycon", "maycon@gmail.com", "password123");
        
        User savedUser = repository.save(userToCreate);
        Optional<User> resultOptional = repository.findByEmail(savedUser.getEmail());
        
        assertTrue(resultOptional.isPresent());

        User foundUser = resultOptional.get();
        assertEquals("maycon@gmail.com", foundUser.getEmail());
    }
    
    @Test
    void shouldReturnTrueWhenEmailExists() {
        User userToCreate = new User("Maycon", "existisemail@gmail.com", "password123");
        
        User savedUser = repository.save(userToCreate);
        
        boolean emailExists = repository.existsByEmail(savedUser.getEmail());
        assertTrue(emailExists);
    }
    
    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        boolean emailExists = repository.existsByEmail("notfoundemail@gmail.com");
        assertFalse(emailExists);
    }
}
