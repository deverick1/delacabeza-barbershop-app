package com.analistas.barberiapp.dto;


import com.analistas.barberiapp.model.EstadoTurno;
import com.analistas.barberiapp.model.TipoServicio;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TurnoDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private LocalDate fecha;
    private LocalTime hora;
    private TipoServicio servicio;
    private EstadoTurno estado;
    private String notas;
}
