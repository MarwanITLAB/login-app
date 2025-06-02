package com.example.login;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public LoginController(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // ✅ Neue Startseite (index.html)
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 🔐 Login-Seite
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // 🔐 Login-Verarbeitung
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", username);
            model.addAttribute("username", username);
            return "welcome";
        } else {
            model.addAttribute("error", "❌ Benutzername oder Passwort falsch");
            return "login";
        }
    }

    // 📝 Registrierungsseite
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // 📝 Registrierung verarbeiten
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           Model model) {

        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "❌ Benutzername existiert bereits");
            return "register";
        }

        User user = new User(username, password, email);
        userRepository.save(user);
        emailService.sendRegistrationEmail(email, username);

        model.addAttribute("message", "✅ Registrierung erfolgreich! Eine Bestätigungs-E-Mail wurde gesendet.");
        return "register";
    }

    // 👋 Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 🙌 Willkommensseite (optional direkt aufrufbar)
    @GetMapping("/welcome")
    public String showWelcomePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        return "welcome";
    }
}
