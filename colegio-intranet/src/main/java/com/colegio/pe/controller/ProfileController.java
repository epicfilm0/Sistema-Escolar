package com.colegio.pe.controller;

import com.colegio.pe.model.User;
import com.colegio.pe.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public ProfileController(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @GetMapping
    public String perfil(Model model, Authentication auth) {
        User user = repo.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("user", user);
        return "layout/profile";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute("user") User user, Authentication auth) {
        User actual = repo.findByUsername(auth.getName()).orElseThrow();

        actual.setNombres(user.getNombres());
        actual.setApellidos(user.getApellidos());
        actual.setEmail(user.getEmail());
        actual.setTelefono(user.getTelefono());
        actual.setDireccion(user.getDireccion());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            actual.setPassword(encoder.encode(user.getPassword()));
        }

        repo.save(actual);
        return "redirect:/profile?success";
    }
}
