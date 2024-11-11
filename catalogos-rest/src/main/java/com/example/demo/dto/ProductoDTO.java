// src/main/java/com/example/demo/dto/ProductoDTO.java
package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

public class ProductoDTO {

    private Long id;

    @NotNull(message = "El campo nombre es obligatorio")
    @Size(min = 1, max = 255, message = "El campo nombre debe tener entre 1 y 255 caracteres")
    private String nombre;

    @NotNull(message = "El campo codigo es obligatorio")
    @Size(min = 1, max = 50, message = "El campo codigo debe tener entre 1 y 50 caracteres")
    private String codigo;

    private String talle;
    private String foto;
    private String color;

    @Positive(message = "El campo cantidad_stock_proveedor debe ser positivo")
    private Integer cantidadStockProveedor;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTalle() {
        return talle;
    }

    public void setTalle(String talle) {
        this.talle = talle;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCantidadStockProveedor() {
        return cantidadStockProveedor;
    }

    public void setCantidadStockProveedor(Integer cantidadStockProveedor) {
        this.cantidadStockProveedor = cantidadStockProveedor;
    }
}
