package com.colegio.pe.controller;

import com.colegio.pe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addUserDetails(Model model, Authentication auth) {
        if (auth != null) {
            userRepository.findByUsername(auth.getName()).ifPresent(user -> {
                //String nombres = user.getNombres() != null ? user.getNombres() : "";
                //String apellidos = user.getApellidos() != null ? user.getApellidos() : "";
                String correo = user.getEmail() != null ? user.getEmail() : "";
                String rol = (user.getRole() != null && user.getRole().getNombre() != null)
                        ? user.getRole().getNombre().replace("ROLE_", "")
                        : "Sin Rol";

                //model.addAttribute("username", nombres + " " + apellidos);
                model.addAttribute("email", correo);
                model.addAttribute("rol", rol);
            });
        } else {
            model.addAttribute("username", "Invitado");
            model.addAttribute("email", "");
            model.addAttribute("rol", "Sin Rol");
        }
    }
}