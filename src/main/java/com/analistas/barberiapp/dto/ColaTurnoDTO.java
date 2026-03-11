package com.analistas.barberiapp.dto;

import com.analistas.barberiapp.model.TipoServicio;

import java.time.LocalDate;
import java.time.LocalTime;

import com.analistas.barberiapp.model.EstadoCola;
import lombok.Data;

@Data
public class ColaTurnoDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private LocalDate fecha;
    private LocalTime horaLlegada;
    private Integer posicion;
    private EstadoCola estado;
    private TipoServicio servicio;
}
