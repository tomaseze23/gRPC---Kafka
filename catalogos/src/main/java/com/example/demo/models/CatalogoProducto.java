package com.example.demo.models;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "catalogo_producto")
@Data
public class CatalogoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "catalogo_id", referencedColumnName = "id", nullable = false)
    private Catalogo catalogo;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    private Producto producto;
}
