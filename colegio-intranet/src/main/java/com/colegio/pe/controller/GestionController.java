package com.colegio.pe.controller;

import com.colegio.pe.model.Gestion;
import com.colegio.pe.repository.GestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/gestiones")
public class GestionController {

    @Autowired
    GestionRepository repo;

    @GetMapping
    public String listar(Model model) {
        List<Gestion> gestiones = repo.findAll();
        model.addAttribute("gestiones", gestiones);
        return "gestion/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("gestion", new Gestion());
        return "gestion/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("gestion") Gestion gestion) {
        if (gestion.getFecha() == null) {
            gestion.setFecha(LocalDate.now());
        }
        repo.save(gestion);
        return "redirect:/gestiones?success";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Gestion g = repo.findById(id).orElseThrow(() -> new RuntimeException("Gestión no encontrada"));
        model.addAttribute("gestion", g);
        return "gestion/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/gestiones?deleted";
    }
}
