package com.colegio.pe.controller;

import com.colegio.pe.model.Role;
import com.colegio.pe.model.User;
import com.colegio.pe.repository.RoleRepository;
import com.colegio.pe.repository.UserRepository;
import com.colegio.pe.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository repo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserUtils userUtils;

    @GetMapping
    public String listar(Model model) {
        List<User> user = repo.findAll();
        model.addAttribute("users", user);
        return "user/list";
    }

    @GetMapping("/nuevo")
    public String nuevoUser(Model model) {
        User user = new User();
        String username = userUtils.generarUsername("C");
        user.setUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepo.findAll());
        return "user/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("user") User user) {
        if (user.getFecha() == null) {
            user.setFecha(LocalDate.now());
        }

        if (user.getRole() != null && user.getRole().getId() != null) {
            Role rol = roleRepo.findById(user.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            user.setRole(rol);
        } else {
            throw new RuntimeException("Debe seleccionar un rol");
        }

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
        return "redirect:/user?success";
    }

    @GetMapping("/editar/{id}")
    public String editarUser(@PathVariable Long id, Model model) {
        User user = repo.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepo.findAll());
        return "user/form";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/user?deleted";
    }
}
