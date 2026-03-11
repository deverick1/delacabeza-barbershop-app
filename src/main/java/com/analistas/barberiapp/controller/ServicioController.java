package com.analistas.barberiapp.controller;

import com.analistas.barberiapp.model.Servicio;
import com.analistas.barberiapp.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<Servicio>> listar() {
        return ResponseEntity.ok(servicioService.listarTodos());
    }

    @PatchMapping("/{id}/precio")
    public ResponseEntity<Servicio> actualizarPrecio(
            @PathVariable Long id,
            @RequestBody Map<String, BigDecimal> body) {
        return ResponseEntity.ok(servicioService.actualizarPrecio(id, body.get("precio")));
    }

    @PostMapping
public ResponseEntity<Servicio> crear(@RequestBody Map<String, Object> body) {
    String nombre = body.get("nombre").toString();
    BigDecimal precio = new BigDecimal(body.get("precio").toString());
    Servicio s = new Servicio();
    s.setNombre(nombre);
    s.setPrecio(precio);
    return ResponseEntity.status(201).body(servicioService.guardar(s));
}
}