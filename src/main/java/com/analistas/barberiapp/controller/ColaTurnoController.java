package com.analistas.barberiapp.controller;

import com.analistas.barberiapp.dto.ColaTurnoDTO;
import com.analistas.barberiapp.dto.ColaTurnoRequest;
import com.analistas.barberiapp.model.EstadoCola;
import com.analistas.barberiapp.service.ColaTurnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cola")
@RequiredArgsConstructor
public class ColaTurnoController {

    private final ColaTurnoService colaTurnoService;

    @GetMapping
    public ResponseEntity<List<ColaTurnoDTO>> listarCola() {
        return ResponseEntity.ok(colaTurnoService.listarColaDeDia());
    }

    @GetMapping("/esperando")
    public ResponseEntity<List<ColaTurnoDTO>> listarEsperando() {
        return ResponseEntity.ok(colaTurnoService.listarEsperando());
    }

    @PostMapping
    public ResponseEntity<ColaTurnoDTO> agregarALaCola(@Valid @RequestBody ColaTurnoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(colaTurnoService.agregarALaCola(request));
    }

    @PostMapping("/siguiente")
    public ResponseEntity<ColaTurnoDTO> llamarSiguiente() {
        return ResponseEntity.ok(colaTurnoService.llamarSiguiente());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ColaTurnoDTO> cambiarEstado(@PathVariable Long id,
                                                       @RequestParam EstadoCola estado) {
        return ResponseEntity.ok(colaTurnoService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        colaTurnoService.eliminarDeLaCola(id);
        return ResponseEntity.noContent().build();
    }
}