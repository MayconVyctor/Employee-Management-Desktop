package com.employeecontrol.infrastructure.database;

import com.employeecontrol.infrastructure.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // Em producao essas credenciais viriam de variaveis de ambiente (.env),
    // mas deixei valores padrao para facilitar a execucao e os testes locais pelo Docker.
    
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/employee_control";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "postgrespassword";
    
    private DatabaseConnection(){
       // Construtor privado para evitar instanciacao
    }
    
    public static Connection getConnection() {
       String url = getEnv("DB_URL", DEFAULT_URL);
       String user = getEnv("DB_USER", DEFAULT_USER);
       String password = getEnv("DB_PASSWORD", DEFAULT_PASSWORD);
       
       try{
           return DriverManager.getConnection(url, user, password);
       }catch (SQLException err) {
           throw new DatabaseException("Error connecting to the database", err);
       }
    }
    
    public static String getEnv(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }
    
}
