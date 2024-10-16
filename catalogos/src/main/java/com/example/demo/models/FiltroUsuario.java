package com.example.demo.models;

import lombok.Data;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "filtros_usuario")
@Data
public class FiltroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre_filtro", nullable = false)
    private String nombreFiltro;

    @Column(name = "filtro", nullable = false)
    private String filtro;  // Consider changing to a suitable type for JSON.

    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;
}

