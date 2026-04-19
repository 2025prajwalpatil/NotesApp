package com.notesapp.service;

import com.notesapp.model.User;
import com.notesapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserService — handles registration and user lookup.
 * Implements UserDetailsService so Spring Security can authenticate users.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new user. Encodes password with BCrypt before saving.
     *
     * @param username desired username
     * @param rawPassword plain-text password entered by user
     * @return the saved User, or empty if username already taken
     */
    public Optional<User> register(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            return Optional.empty(); // username already taken
        }
        User user = new User(username, passwordEncoder.encode(rawPassword));
        return Optional.of(userRepository.save(user));
    }

    /** Find a user by username (used internally). */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Required by Spring Security — loads a user by username for authentication.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Return Spring Security's built-in User (roles: USER)
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
