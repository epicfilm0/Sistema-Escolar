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
    private String nombre;
    private String turno;
    private String estado;
}
