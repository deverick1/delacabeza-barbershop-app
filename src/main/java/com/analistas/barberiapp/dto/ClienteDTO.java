package com.analistas.barberiapp.dto;


import lombok.Data;

@Data
public class ClienteDTO {
    private Long id;
    private String nombre;
    private String telefono;
    private String email;
}
