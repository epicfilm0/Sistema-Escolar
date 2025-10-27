package com.colegio.pe.data;

import com.colegio.pe.model.Role;
import com.colegio.pe.model.User;
import com.colegio.pe.repository.RoleRepository;
import com.colegio.pe.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner init(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        return args -> {
            Role adminRole = roleRepo.findByNombre("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setNombre("ROLE_ADMIN");
                adminRole.setDescripcion("Administrador del sistema");
                roleRepo.save(adminRole);
            }

            Role profRole = roleRepo.findByNombre("ROLE_PROFESOR");
            if (profRole == null) {
                profRole = new Role();
                profRole.setNombre("ROLE_PROFESOR");
                profRole.setDescripcion("Profesor del colegio");
                roleRepo.save(profRole);
            }

            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                //admin.setNombres("Luis");
                //admin.setApellidos("Aponte");
                admin.setEmail("luisaponte.official@gmail.com");
                //admin.setTelefono("927586875");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(adminRole);
                userRepo.save(admin);
            }

            if (userRepo.findByUsername("profesor").isEmpty()) {
                User prof = new User();
                prof.setUsername("profesor");
                prof.setPassword(encoder.encode("prof123"));
                prof.setRole(profRole);
                userRepo.save(prof);
            }
        };
    }
}
