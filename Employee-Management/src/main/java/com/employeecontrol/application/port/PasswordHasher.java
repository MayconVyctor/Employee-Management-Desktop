package com.employeecontrol.application.port;

public interface PasswordHasher {

    String hash(String password);

}