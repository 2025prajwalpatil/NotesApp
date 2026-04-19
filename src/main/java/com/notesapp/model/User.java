package com.notesapp.model;

import jakarta.persistence.*;

/**
 * User entity — stores login credentials.
 * Password is stored as a BCrypt hash (never plain text).
 */
@Entity
@Table(name = "app_user") // "user" is a reserved keyword in H2
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password; // BCrypt encoded

    // ---- Constructors ----

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ---- Getters & Setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
