package com.notesapp.repository;

import com.notesapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Find a user by their unique username (used during login and registration). */
    Optional<User> findByUsername(String username);
}
