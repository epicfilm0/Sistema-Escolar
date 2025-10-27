package com.colegio.pe.controller;

import com.colegio.pe.model.Curso;
import com.colegio.pe.repository.CursoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository repo;

    public CursoController(CursoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listar(Model model) {
        List<Curso> cursos = repo.findAll();
        model.addAttribute("cursos", cursos);
        return "curso/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("curso", new Curso());
        return "curso/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("curso") Curso curso) {
        if (curso.getFecha() == null) {
            curso.setFecha(LocalDate.now());
        }
        repo.save(curso);
        return "redirect:/cursos?success";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Curso c = repo.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        model.addAttribute("curso", c);
        return "curso/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/cursos?deleted";
    }
}
