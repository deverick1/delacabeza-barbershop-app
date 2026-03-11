package com.analistas.barberiapp.config;

import com.analistas.barberiapp.model.Servicio;
import com.analistas.barberiapp.repository.ServicioRepository;
import com.analistas.barberiapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AuthService authService;
    private final ServicioRepository servicioRepository;

    @Override
    public void run(String... args) {
        // Usuario admin
        try {
            authService.registrar("Dueño", "admin@barberia.com", "admin123");
            log.info("Usuario inicial creado: admin@barberia.com / admin123");
        } catch (RuntimeException e) {
            log.info("El usuario admin ya existe, no se creó uno nuevo.");
        }

        // Servicios iniciales
        crearServicioSiNoExiste("CORTE", new BigDecimal("1500"));
        crearServicioSiNoExiste("BARBA", new BigDecimal("1000"));
        crearServicioSiNoExiste("COMBO", new BigDecimal("2000"));
    }

    private void crearServicioSiNoExiste(String nombre, BigDecimal precio) {
        if (servicioRepository.findByNombre(nombre).isEmpty()) {
            Servicio s = new Servicio();
            s.setNombre(nombre);
            s.setPrecio(precio);
            servicioRepository.save(s);
            log.info("Servicio creado: {} - ${}", nombre, precio);
        }
    }
}