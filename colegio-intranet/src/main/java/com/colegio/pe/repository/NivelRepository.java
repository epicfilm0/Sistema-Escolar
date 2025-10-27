package com.colegio.pe.repository;

import com.colegio.pe.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NivelRepository extends JpaRepository<Nivel, Long> {
    List<Nivel> findByEstado(String estado);
}
