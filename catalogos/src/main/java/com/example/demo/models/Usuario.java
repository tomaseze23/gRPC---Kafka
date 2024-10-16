package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre_usuario", unique = true, nullable = false)
    private String nombreUsuario;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "tienda_id", referencedColumnName = "codigo", nullable = true)
    private Tienda tienda;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "habilitado", nullable = false)
    private boolean habilitado = true;
}

