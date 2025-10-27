package com.colegio.pe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "niveles")
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gestion_id")
    private Gestion gestion;

    private String nombre; // Inicial, Primaria, Secundaria
    private String turno; // Mañana, Tarde, Noche
    private String estado; // Activo / Inactivo

}
