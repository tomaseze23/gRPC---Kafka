// src/main/java/com/example/demo/dto/OrdenCompraCentralDTO.java
package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class OrdenCompraCentralDTO {

    private Long id;

    @NotNull(message = "El campo tiendaId es obligatorio")
    @Size(min = 1, max = 50, message = "El campo tiendaId debe tener entre 1 y 50 caracteres")
    private String tiendaId;

    @NotNull(message = "El campo código de artículo es obligatorio")
    @Size(min = 1, max = 50, message = "El campo código de artículo debe tener entre 1 y 50 caracteres")
    private String codigoArticulo;

    @NotNull(message = "El campo color es obligatorio")
    @Size(min = 1, max = 20, message = "El campo color debe tener entre 1 y 20 caracteres")
    private String color;

    @NotNull(message = "El campo talle es obligatorio")
    @Size(min = 1, max = 20, message = "El campo talle debe tener entre 1 y 20 caracteres")
    private String talle;

    @NotNull(message = "El campo cantidad solicitada es obligatorio")
    @Positive(message = "La cantidad solicitada debe ser un número positivo")
    private Integer cantidadSolicitada;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(String tiendaId) {
        this.tiendaId = tiendaId;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTalle() {
        return talle;
    }

    public void setTalle(String talle) {
        this.talle = talle;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }
}