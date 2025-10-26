package com.colegio.pe.controller;

import com.colegio.pe.model.Grado;
import com.colegio.pe.repository.GradoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/grados")
public class GradoController {

    private final GradoRepository repo;

    public GradoController(GradoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listar(Model model) {
        List<Grado> grados = repo.findAll();
        model.addAttribute("grados", grados);
        return "grados/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Grado grado = new Grado();
        grado.setFecha(LocalDate.now());
        model.addAttribute("grado", grado);
        return "grados/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("grado") Grado grado) {
        if (grado.getFecha() == null) {
            grado.setFecha(LocalDate.now());
        }
        repo.save(grado);
        return "redirect:/grados?success";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Grado grado = repo.findById(id).orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        model.addAttribute("grado", grado);
        return "grados/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/grados?deleted";
    }
}
