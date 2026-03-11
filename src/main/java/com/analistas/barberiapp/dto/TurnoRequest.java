package com.analistas.barberiapp.dto;

import com.analistas.barberiapp.model.TipoServicio;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TurnoRequest {
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;

    @NotNull(message = "El servicio es obligatorio")
    private TipoServicio servicio;

    private String notas;
}
