package com.colegio.pe.controller;

import com.colegio.pe.model.Gestion;
import com.colegio.pe.model.Nivel;
import com.colegio.pe.repository.GestionRepository;
import com.colegio.pe.repository.NivelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/niveles")
public class NivelController {

    @Autowired
    NivelRepository nivelRepo;
    @Autowired
    GestionRepository gestionRepo;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("niveles", nivelRepo.findAll());
        return "nivel/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("nivel", new Nivel());
        model.addAttribute("gestiones", gestionRepo.findAll());
        return "nivel/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Nivel nivel) {
        System.out.printf(
                ">>> Nivel (save): nombre=%s, turno=%s, estado=%s, gestion_id=%s%n",
                nivel.getNombre(), nivel.getTurno(), nivel.getEstado(),
                nivel.getGestion() != null ? nivel.getGestion().getId() : "null"
        );

        if (nivel.getGestion() != null && nivel.getGestion().getId() != null) {
            Gestion g = gestionRepo.findById(nivel.getGestion().getId()).orElse(null);
            nivel.setGestion(g);
        }

        nivelRepo.save(nivel);
        return "redirect:/niveles?success";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Nivel nivel = nivelRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado"));
        model.addAttribute("nivel", nivel);
        model.addAttribute("gestiones", gestionRepo.findAll());
        return "nivel/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        nivelRepo.deleteById(id);
        return "redirect:/niveles?deleted";
    }
}
