package com.colegio.pe.controller;

import com.colegio.pe.model.Role;
import com.colegio.pe.model.User;
import com.colegio.pe.repository.RoleRepository;
import com.colegio.pe.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository repo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public AdminController(UserRepository repo, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.repo = repo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }

    @GetMapping
    public String listar(Model model) {
        Role adminRole = roleRepo.findByNombre("ROLE_ADMIN");
        List<User> admins = repo.findAll().stream()
                .filter(u -> u.getRole() != null && u.getRole().equals(adminRole))
                .collect(Collectors.toList());
        model.addAttribute("admins", admins);
        return "admin/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("user") User user) {
        Role adminRole = roleRepo.findByNombre("ROLE_ADMIN");

        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setNombre("ROLE_ADMIN");
            adminRole.setDescripcion("Administrador del sistema");
            roleRepo.save(adminRole);
        }

        user.setRole(adminRole);

        if (user.getId() == null) {
            user.setPassword(encoder.encode(user.getPassword()));
        } else {
            repo.findById(user.getId()).ifPresent(existing -> {
                if (user.getPassword() == null || user.getPassword().isEmpty()) {
                    user.setPassword(existing.getPassword());
                } else {
                    user.setPassword(encoder.encode(user.getPassword()));
                }
            });
        }

        repo.save(user);

        return "redirect:/admin?success";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        User admin = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        model.addAttribute("user", admin);
        return "admin/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin?deleted";
    }
}
