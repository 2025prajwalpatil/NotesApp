package com.notesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Login Notes App.
 * Run with: mvn spring-boot:run
 * Access at: http://localhost:8080
 */
@SpringBootApplication
public class LoginNotesAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginNotesAppApplication.class, args);
    }
}
