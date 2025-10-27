package com.colegio.pe.controller;

import com.colegio.pe.model.Grado;
import com.colegio.pe.model.Nivel;
import com.colegio.pe.repository.GradoRepository;
import com.colegio.pe.repository.NivelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/grados")
public class GradoController {

    @Autowired
    private GradoRepository gradoRepository;

    @Autowired
    private NivelRepository nivelRepository;

    @GetMapping
    public String listar(Model model) {
        List<Grado> grados = gradoRepository.findAll();
        model.addAttribute("grados", grados);
        return "grados/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("grado", new Grado());
        model.addAttribute("niveles", nivelRepository.findAll());
        return "grados/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("grado") Grado grado) {
        if (grado.getNivel() != null && grado.getNivel().getId() != null) {
            Nivel nivel = nivelRepository.findById(grado.getNivel().getId())
                    .orElseThrow(() -> new RuntimeException("Nivel no encontrado"));
            grado.setNivel(nivel);
        }
        gradoRepository.save(grado);
        return "redirect:/grados?success";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Grado grado = gradoRepository.findById(id).orElse(new Grado());
        model.addAttribute("grado", grado);
        model.addAttribute("niveles", nivelRepository.findAll());
        return "grados/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        gradoRepository.deleteById(id);
        return "redirect:/grados?deleted";
    }
}
