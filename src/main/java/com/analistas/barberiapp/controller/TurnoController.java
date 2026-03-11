package com.analistas.barberiapp.controller;

import com.analistas.barberiapp.dto.TurnoDTO;
import com.analistas.barberiapp.dto.TurnoRequest;
import com.analistas.barberiapp.model.EstadoTurno;
import com.analistas.barberiapp.service.TurnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) Boolean soloPendientes) {

        LocalDate fechaConsulta = fecha != null ? fecha : LocalDate.now();

        if (Boolean.TRUE.equals(soloPendientes)) {
            return ResponseEntity.ok(turnoService.listarPendientesPorFecha(fechaConsulta));
        }
        return ResponseEntity.ok(turnoService.listarPorFecha(fechaConsulta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> crear(@Valid @RequestBody TurnoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.crear(request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TurnoDTO> cambiarEstado(@PathVariable Long id,
                                                   @RequestParam EstadoTurno estado) {
        return ResponseEntity.ok(turnoService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}