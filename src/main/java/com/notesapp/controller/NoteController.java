package com.notesapp.controller;

import com.notesapp.model.Note;
import com.notesapp.model.User;
import com.notesapp.service.NoteService;
import com.notesapp.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * NoteController — manages the notes page (view, add, delete).
 * All routes require an authenticated session (enforced by SecurityConfig).
 */
@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    /**
     * GET /notes — show all notes for the currently logged-in user.
     *
     * @param userDetails injected by Spring Security from the current session
     */
    @GetMapping
    public String showNotes(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = resolveUser(userDetails);
        List<Note> notes = noteService.getNotesByUser(user);
        model.addAttribute("notes", notes);
        model.addAttribute("username", user.getUsername());
        return "notes"; // → templates/notes.html
    }

    /**
     * POST /notes — add a new note for the logged-in user.
     */
    @PostMapping
    public String addNote(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam String content) {
        if (content != null && !content.isBlank()) {
            User user = resolveUser(userDetails);
            noteService.addNote(content.trim(), user);
        }
        return "redirect:/notes";
    }

    /**
     * GET /delete/{id} — delete a note (only if it belongs to the current user).
     */
    @GetMapping("/delete/{id}")
    public String deleteNote(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable Long id) {
        User user = resolveUser(userDetails);

        // Security check: only delete if the note belongs to this user
        Optional<Note> note = noteService.findById(id);
        if (note.isPresent() && note.get().getUser().getId().equals(user.getId())) {
            noteService.deleteNote(id);
        }
        return "redirect:/notes";
    }

    // ---- Helper ----

    /** Resolve the currently authenticated user's full entity from the DB. */
    private User resolveUser(UserDetails userDetails) {
        return userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found in DB"));
    }
}
