package com.colegio.pe.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String email;
        @Column(nullable = false, unique = true)
        private String username;
        @Column(nullable = false)
        private String password;
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "role_id", nullable = false)
        private Role role;
        private LocalDate fecha;
        private String estado;

        @PrePersist
        public void prePersist() {
            if (fecha == null) {
                fecha = LocalDate.now();
            }
        }
}
