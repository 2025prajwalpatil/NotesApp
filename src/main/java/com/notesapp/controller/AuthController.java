package com.notesapp.controller;

import com.notesapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AuthController — handles registration and login pages.
 * Spring Security handles the actual POST /login form submission automatically.
 */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ----------------------------------------------------------------
    // Registration
    // ----------------------------------------------------------------

    /** Show the registration form. */
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // → templates/register.html
    }

    /**
     * Process registration form submission.
     * On success → redirect to login.
     * On failure (username taken) → show error on register page.
     */
    @PostMapping("/register")
    public String handleRegister(@RequestParam String username,
                                  @RequestParam String password,
                                  Model model) {
        if (username.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Username and password must not be empty.");
            return "register";
        }

        boolean registered = userService.register(username.trim(), password).isPresent();

        if (!registered) {
            model.addAttribute("error", "Username '" + username + "' is already taken. Please choose another.");
            return "register";
        }

        return "redirect:/login?registered=true";
    }

    // ----------------------------------------------------------------
    // Login
    // ----------------------------------------------------------------

    /**
     * Show the login form.
     * Spring Security sends query params: ?error=true or ?logout=true
     */
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) String logout,
                                 @RequestParam(required = false) String registered,
                                 Model model) {
        if (error != null)      model.addAttribute("error",      "Invalid username or password.");
        if (logout != null)     model.addAttribute("message",    "You have been logged out.");
        if (registered != null) model.addAttribute("message",    "Registration successful! Please log in.");
        return "login"; // → templates/login.html
    }
}
