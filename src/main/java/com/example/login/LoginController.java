package com.example.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("username", username);
            return "welcome";
        } else {
            model.addAttribute("error", "❌ Benutzername oder Passwort falsch");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {

        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "❌ Benutzername existiert bereits");
            return "register";
        }

        User user = new User(username, password);
        userRepository.save(user);
        model.addAttribute("message", "✅ Registrierung erfolgreich! Du kannst dich jetzt einloggen.");
        return "register";
    }
}
