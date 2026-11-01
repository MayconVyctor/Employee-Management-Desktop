package com.employeecontrol.infrastructure.repository;

import com.employeecontrol.domain.repository.UserRepository;
import com.employeecontrol.infrastructure.exception.DatabaseException;
import com.employeecontrol.domain.entity.User;
import com.employeecontrol.domain.exception.UserAlreadyExistsException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepositoryJdbc implements UserRepository {
    
    private final Connection connection;
    
    public UserRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public User save(User user) {
        String sql = """
                     INSERT INTO users (name, email, password_hash) 
                     VALUES (?, ?, ?) 
                     RETURNING id, created_at
                     """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPasswordHash());
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) {
                    throw new SQLException("User insertion did not return generated data.");
                }
                return new User(result.getLong("id"),
                                 user.getName(),
                                 user.getEmail(),
                                 user.getPasswordHash(),
                                 result.getTimestamp("created_at").toInstant()
                                 );
            }
        }catch (SQLException err) {
             throw new DatabaseException("Failed to save user to the database", err);
        }
    }
    
    @Override 
    public Optional<User> findByEmail(String email) {
        String sql = """
                     SELECT id, name, email 
                     FROM users WHERE email = ?
                     """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
        }catch (SQLException err) {
             throw new DatabaseException("Failed", err);
        } 
        return Optional.empty();
    }
    
    @Override
    public boolean existsByEmail(String email) {
        String sql = """
                     SELECT EXISTS(SELECT 1 FROM users WHERE email = ?) AS email_exists
                     """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            
            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) {
                    throw new SQLException("Existence query returned no result.");
                }
                return result.getBoolean("email_exists");

            }
        }catch (SQLException err) {
            throw new DatabaseException("Failed to check if user email exists", err);
        }
    }
}
