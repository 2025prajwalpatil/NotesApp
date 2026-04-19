package com.notesapp;

import com.notesapp.model.User;
import com.notesapp.repository.UserRepository;
import com.notesapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic Spring Boot integration tests.
 * Verifies that the application context loads and core flows work.
 */
@SpringBootTest
@Transactional  // rolls back DB changes after each test
class LoginNotesAppApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /** Context loads without errors */
    @Test
    void contextLoads() {
        // If the application context starts, this test passes
    }

    /** Registering a new user persists to the DB */
    @Test
    void shouldRegisterNewUser() {
        Optional<User> result = userService.register("testuser", "password123");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testuser");
        // Password must be BCrypt encoded, not plain-text
        assertThat(result.get().getPassword()).doesNotContain("password123");
        assertThat(result.get().getPassword()).startsWith("$2a$");
    }

    /** Registering with a duplicate username returns empty */
    @Test
    void shouldRejectDuplicateUsername() {
        userService.register("dupuser", "pass1");
        Optional<User> duplicate = userService.register("dupuser", "pass2");

        assertThat(duplicate).isEmpty();
    }

    /** Finding a user by username works */
    @Test
    void shouldFindUserByUsername() {
        userService.register("findme", "secret");
        Optional<User> found = userService.findByUsername("findme");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("findme");
    }
}
