package com.analistas.barberiapp.dto;

import com.analistas.barberiapp.model.TipoServicio;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ColaTurnoRequest {
    
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    private TipoServicio servicio;
    
}
