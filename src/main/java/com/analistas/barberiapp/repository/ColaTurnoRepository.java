package com.analistas.barberiapp.repository;

import com.analistas.barberiapp.model.ColaTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.analistas.barberiapp.model.EstadoCola;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

public interface ColaTurnoRepository extends JpaRepository<ColaTurno, Long> {
    List<ColaTurno> findByFechaOrderByPosicionAsc(LocalDate fecha);
    List<ColaTurno> findByFechaAndEstadoOrderByPosicionAsc(LocalDate fecha, EstadoCola estado);
    Optional<ColaTurno> findByFechaAndEstado(LocalDate fecha, EstadoCola estado);

    @Query("SELECT COALESCE(MAX(c.posicion), 0) FROM ColaTurno c WHERE c.fecha = :fecha")
    Integer findMaxPosicionByFecha(LocalDate fecha);
}