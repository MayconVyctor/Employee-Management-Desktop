package com.employeecontrol;

import java.sql.Connection;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import com.employeecontrol.application.usecase.LoginUserUseCase;
import com.employeecontrol.application.usecase.RegisterUserUseCase;
import com.employeecontrol.application.usecase.RegisterEmployeeUseCase;
import com.employeecontrol.application.usecase.ListEmployeesUseCase;

import com.employeecontrol.infrastructure.database.DatabaseConnection;
import com.employeecontrol.infrastructure.repository.UserRepositoryJdbc;
import com.employeecontrol.infrastructure.repository.EmployeeRepositoryJdbc;
import com.employeecontrol.infrastructure.security.Sha256PasswordHasher;

import com.employeecontrol.presentation.view.LoginFrame;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                Connection connection = DatabaseConnection.getConnection();

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        connection.close();
                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                }));

                UserRepositoryJdbc userRepository =
                    new UserRepositoryJdbc(connection);

                EmployeeRepositoryJdbc employeeRepository =
                    new EmployeeRepositoryJdbc(connection);

                Sha256PasswordHasher passwordHasher =
                    new Sha256PasswordHasher();

                LoginUserUseCase loginUserUseCase =
                    new LoginUserUseCase(userRepository, passwordHasher);

                RegisterUserUseCase registerUserUseCase =
                    new RegisterUserUseCase(userRepository, passwordHasher);

                RegisterEmployeeUseCase registerEmployeeUseCase =
                    new RegisterEmployeeUseCase(employeeRepository);

                ListEmployeesUseCase listEmployeesUseCase =
                    new ListEmployeesUseCase(employeeRepository);
                
                LoginFrame loginFrame = new LoginFrame(
                        loginUserUseCase,
                        registerUserUseCase,
                        registerEmployeeUseCase,
                        listEmployeesUseCase
                );

                loginFrame.setVisible(true);

            } catch (Exception err) {
                JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível iniciar a aplicação:\n" + err.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}