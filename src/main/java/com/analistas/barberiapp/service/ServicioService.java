package com.analistas.barberiapp.service;

import com.analistas.barberiapp.model.Servicio;
import com.analistas.barberiapp.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public Servicio actualizarPrecio(Long id, BigDecimal nuevoPrecio) {
        Servicio servicio = servicioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setPrecio(nuevoPrecio);
        return servicioRepository.save(servicio);
    }

    public Servicio guardar(Servicio servicio) {
    return servicioRepository.save(servicio);
}
}