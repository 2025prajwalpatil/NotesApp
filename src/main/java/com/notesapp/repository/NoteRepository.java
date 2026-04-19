package com.notesapp.repository;

import com.notesapp.model.Note;
import com.notesapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for Note entities.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    /** Return all notes that belong to the given user, ordered newest first. */
    List<Note> findByUserOrderByIdDesc(User user);
}
