package com.example.demo.models;

import lombok.Data;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "catalogos")
@Data
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tienda_id", referencedColumnName = "codigo", nullable = false)
    private Tienda tienda;

    @Column(name = "nombre_catalogo", nullable = false)
    private String nombreCatalogo;

    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;
}
