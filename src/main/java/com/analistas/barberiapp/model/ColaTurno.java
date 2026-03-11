package com.analistas.barberiapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "cola_turnos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ColaTurno {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime horaLlegada;

    @Column(nullable = false)
    private Integer posicion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoCola estado = EstadoCola.ESPERANDO;

    @Enumerated(EnumType.STRING)
    private TipoServicio servicio;
}
