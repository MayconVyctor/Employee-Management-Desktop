package com.employeecontrol.domain.repository;

import com.employeecontrol.domain.entity.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
}

