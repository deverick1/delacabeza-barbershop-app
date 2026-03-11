package com.analistas.barberiapp.repository;

import com.analistas.barberiapp.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Optional<Servicio> findByNombre(String nombre);
}