// src/main/java/com/example/demo/dto/CatalogoProductoDTO.java
package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CatalogoProductoDTO {

    private Long id;

    @NotNull(message = "El campo catalogoId es obligatorio")
    @Positive(message = "El campo catalogoId debe ser positivo")
    private Long catalogoId;

    @NotNull(message = "El campo nombreProducto es obligatorio")
    @Size(min = 1, max = 255, message = "El campo nombreProducto debe tener entre 1 y 255 caracteres")
    private String nombreProducto;

    @NotNull(message = "El campo precio es obligatorio")
    @Positive(message = "El campo precio debe ser positivo")
    private Double precio;

    private String descripcion;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCatalogoId() {
        return catalogoId;
    }

    public void setCatalogoId(Long catalogoId) {
        this.catalogoId = catalogoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
