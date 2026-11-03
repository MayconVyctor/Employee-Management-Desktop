package com.employeecontrol.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Sha256PasswordHasherTest {

    private final Sha256PasswordHasher hasher = new Sha256PasswordHasher();

    @Test
    void shouldReturn64CharacterHash() {
        String result = hasher.hash("password123");

        assertNotNull(result);
        assertEquals(64, result.length());
    }

    @Test
    void shouldReturnSameHashForSamePassword() {
        String firstHash = hasher.hash("password123");
        String secondHash = hasher.hash("password123");

        assertEquals(firstHash, secondHash);
    }
}