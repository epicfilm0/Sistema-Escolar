package com.colegio.pe.repository;

import com.colegio.pe.model.Gestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GestionRepository extends JpaRepository<Gestion, Long> {
    List<Gestion> findByEstado(String estado);
}
