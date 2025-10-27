package com.colegio.pe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "grados")
public class Grado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nivel_id")
    private Nivel nivel;
    private String nombre;
    private String paralelo;
}
