package com.codewithmosh.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String lugar;
    private Integer cupoMaximo;
    private Boolean publicado = false;
    private Boolean cancelado = false;

    @ManyToOne
    @JoinColumn(name = "organizador_id")
    private Usuario organizador;
}