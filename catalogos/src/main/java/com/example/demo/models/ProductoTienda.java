package com.example.demo.models;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "producto_tienda")
@Data
public class ProductoTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "tienda_id", referencedColumnName = "codigo", nullable = false)
    private Tienda tienda;

    @Column(name = "color")
    private String color;

    @Column(name = "talle")
    private String talle;

    @Column(name = "cantidad", nullable = false)
    private int cantidad = 0;
}

