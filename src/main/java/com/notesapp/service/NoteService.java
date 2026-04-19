package com.notesapp.service;

import com.notesapp.model.Note;
import com.notesapp.model.User;
import com.notesapp.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * NoteService — handles CRUD operations on notes.
 */
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Add a new note for the given user.
     *
     * @param content note text
     * @param user    owner of the note
     */
    public Note addNote(String content, User user) {
        return noteRepository.save(new Note(content, user));
    }

    /**
     * Retrieve all notes belonging to the given user (newest first).
     */
    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByUserOrderByIdDesc(user);
    }

    /**
     * Delete a note by its ID.
     * The controller is responsible for verifying ownership before calling this.
     */
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    /**
     * Find a note by ID (used for ownership verification).
     */
    public java.util.Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }
}
