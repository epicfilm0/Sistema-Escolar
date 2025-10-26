package com.colegio.pe.controller;

import com.colegio.pe.model.Role;
import com.colegio.pe.repository.RoleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository repo;

    public RoleController(RoleRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listar(Model model) {
        List<Role> roles = repo.findAll();
        model.addAttribute("roles", roles);
        return "roles/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("role", new Role());
        return "roles/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Role role) {
        repo.save(role);
        return "redirect:/roles?success";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Role role = repo.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        model.addAttribute("role", role);
        return "roles/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/roles?deleted";
    }
}
