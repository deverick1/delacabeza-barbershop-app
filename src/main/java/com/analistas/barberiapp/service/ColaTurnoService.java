package com.analistas.barberiapp.service;

import com.analistas.barberiapp.dto.ColaTurnoDTO;
import com.analistas.barberiapp.dto.ColaTurnoRequest;
import com.analistas.barberiapp.model.Cliente;
import com.analistas.barberiapp.model.ColaTurno;
import com.analistas.barberiapp.model.EstadoCola;
import com.analistas.barberiapp.repository.ClienteRepository;
import com.analistas.barberiapp.repository.ColaTurnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColaTurnoService {

    private final ColaTurnoRepository colaTurnoRepository;
    private final ClienteRepository clienteRepository;

    public List<ColaTurnoDTO> listarColaDeDia() {
        return colaTurnoRepository.findByFechaOrderByPosicionAsc(LocalDate.now()).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ColaTurnoDTO> listarEsperando() {
        return colaTurnoRepository
                .findByFechaAndEstadoOrderByPosicionAsc(LocalDate.now(), EstadoCola.ESPERANDO).stream()
                .map(this::toDTO)
                .toList();
    }

    public ColaTurnoDTO agregarALaCola(ColaTurnoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Integer ultimaPosicion = colaTurnoRepository.findMaxPosicionByFecha(LocalDate.now());
        int nuevaPosicion = ultimaPosicion + 1;

        ColaTurno entrada = ColaTurno.builder()
                .cliente(cliente)
                .fecha(LocalDate.now())
                .horaLlegada(LocalTime.now())
                .posicion(nuevaPosicion)
                .servicio(request.getServicio())
                .build();

        return toDTO(colaTurnoRepository.save(entrada));
    }

    public ColaTurnoDTO llamarSiguiente() {
        // Marcar el actual EN_ATENCION como COMPLETADO
        colaTurnoRepository.findByFechaAndEstado(LocalDate.now(), EstadoCola.EN_ATENCION)
                .ifPresent(actual -> {
                    actual.setEstado(EstadoCola.COMPLETADO);
                    colaTurnoRepository.save(actual);
                });

        // Traer el siguiente ESPERANDO
        List<ColaTurno> esperando = colaTurnoRepository
                .findByFechaAndEstadoOrderByPosicionAsc(LocalDate.now(), EstadoCola.ESPERANDO);

        if (esperando.isEmpty()) {
            throw new RuntimeException("No hay más clientes esperando");
        }

        ColaTurno siguiente = esperando.get(0);
        siguiente.setEstado(EstadoCola.EN_ATENCION);
        return toDTO(colaTurnoRepository.save(siguiente));
    }

    public ColaTurnoDTO cambiarEstado(Long id, EstadoCola nuevoEstado) {
        ColaTurno entrada = colaTurnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrada de cola no encontrada"));
        entrada.setEstado(nuevoEstado);
        return toDTO(colaTurnoRepository.save(entrada));
    }

    public void eliminarDeLaCola(Long id) {
        if (!colaTurnoRepository.existsById(id)) {
            throw new RuntimeException("Entrada no encontrada con id: " + id);
        }
        colaTurnoRepository.deleteById(id);
    }

    private ColaTurnoDTO toDTO(ColaTurno c) {
        ColaTurnoDTO dto = new ColaTurnoDTO();
        dto.setId(c.getId());
        dto.setClienteId(c.getCliente().getId());
        dto.setClienteNombre(c.getCliente().getNombre());
        dto.setFecha(c.getFecha());
        dto.setHoraLlegada(c.getHoraLlegada());
        dto.setPosicion(c.getPosicion());
        dto.setEstado(c.getEstado());
        dto.setServicio(c.getServicio());
        return dto;
    }
}