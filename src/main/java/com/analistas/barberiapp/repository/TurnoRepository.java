package com.analistas.barberiapp.repository;

import com.analistas.barberiapp.model.EstadoTurno;
import com.analistas.barberiapp.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByFechaOrderByHoraAsc(LocalDate fecha);
    List<Turno> findByFechaAndEstadoOrderByHoraAsc(LocalDate fecha, EstadoTurno estado);
    boolean existsByFechaAndHora(LocalDate fecha, LocalTime hora);
}