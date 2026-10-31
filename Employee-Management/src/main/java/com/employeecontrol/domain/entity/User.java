package com.employeecontrol.domain.entity;

import java.time.Instant;

public class User {
    
    private final Long id;
    private final String name;
    private final String email;
    private final String passwordHash;
    private final Instant createdAt;
    
    
    public User(Long id, String name, String email, String passwordHash, Instant createdAt) {
        validateIdAndCreatedAt(id, createdAt);
        this.id = id;
        this.name = validateNoBlank(name, "name");
        this.email = validateNoBlank(email, "email");
        this.passwordHash = validateNoBlank(passwordHash, "passwordHash");
        this.createdAt = createdAt;
    }
    
    public User(String name, String email, String passwordHash) { 
        this(null, name, email, passwordHash, null);
    }
    
    private static void validateIdAndCreatedAt(Long id, Instant createdAt) {
        boolean newUser = id == null && createdAt == null;
        boolean persistedUser = id != null && createdAt != null;
        if (!newUser && !persistedUser) {
            throw new IllegalArgumentException(
                "id and createdAt must both be null (new user) or both be set (persisted user)");
        }
    }
     
    private static String validateNoBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("The " + field + " is required");
        }
        return value;
    }
        
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
