package com.analistas.barberiapp.service;

import com.analistas.barberiapp.dto.TurnoDTO;
import com.analistas.barberiapp.dto.TurnoRequest;
import com.analistas.barberiapp.model.Cliente;
import com.analistas.barberiapp.model.EstadoTurno;
import com.analistas.barberiapp.model.Turno;
import com.analistas.barberiapp.repository.ClienteRepository;
import com.analistas.barberiapp.repository.TurnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoService {
    private final TurnoRepository turnoRepository;
    private final ClienteRepository clienteRepository;

    public List<TurnoDTO> listarPorFecha(LocalDate fecha) {
        return turnoRepository.findByFechaOrderByHoraAsc(fecha).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<TurnoDTO> listarPendientesPorFecha(LocalDate fecha) {
        return turnoRepository.findByFechaAndEstadoOrderByHoraAsc(fecha, EstadoTurno.PENDIENTE).stream()
                .map(this::toDTO)
                .toList();
    }

    public TurnoDTO obtenerPorId(Long id) {
        return toDTO(findById(id));
    }

    public TurnoDTO crear(TurnoRequest request) {
        if (turnoRepository.existsByFechaAndHora(request.getFecha(), request.getHora())) {
            throw new RuntimeException("Ya existe un turno para esa fecha y hora");
        }

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Turno turno = Turno.builder()
                .cliente(cliente)
                .fecha(request.getFecha())
                .hora(request.getHora())
                .servicio(request.getServicio())
                .notas(request.getNotas())
                .build();

        return toDTO(turnoRepository.save(turno));
    }

    public TurnoDTO cambiarEstado(Long id, EstadoTurno nuevoEstado) {
        Turno turno = findById(id);
        turno.setEstado(nuevoEstado);
        return toDTO(turnoRepository.save(turno));
    }

    public void eliminar(Long id) {
        if (!turnoRepository.existsById(id)) {
            throw new RuntimeException("Turno no encontrado con id: " + id);
        }
        turnoRepository.deleteById(id);
    }

    private Turno findById(Long id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado con id: " + id));
    }

    private TurnoDTO toDTO(Turno t) {
        TurnoDTO dto = new TurnoDTO();
        dto.setId(t.getId());
        dto.setClienteId(t.getCliente().getId());
        dto.setClienteNombre(t.getCliente().getNombre());
        dto.setFecha(t.getFecha());
        dto.setHora(t.getHora());
        dto.setServicio(t.getServicio());
        dto.setEstado(t.getEstado());
        dto.setNotas(t.getNotas());
        return dto;
    }
}

