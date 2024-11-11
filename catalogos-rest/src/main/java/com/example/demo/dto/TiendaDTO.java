// src/main/java/com/example/demo/dto/TiendaDTO.java
package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TiendaDTO {

    @NotNull(message = "El campo codigo es obligatorio")
    @Size(min = 1, max = 50, message = "El campo codigo debe tener entre 1 y 50 caracteres")
    private String codigo;

    @NotNull(message = "El campo direccion es obligatorio")
    @Size(min = 1, max = 255, message = "El campo direccion debe tener entre 1 y 255 caracteres")
    private String direccion;

    @NotNull(message = "El campo ciudad es obligatorio")
    @Size(min = 1, max = 100, message = "El campo ciudad debe tener entre 1 y 100 caracteres")
    private String ciudad;

    @NotNull(message = "El campo provincia es obligatorio")
    @Size(min = 1, max = 100, message = "El campo provincia debe tener entre 1 y 100 caracteres")
    private String provincia;

    private Boolean habilitada;

    // Getters y Setters

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Boolean getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(Boolean habilitada) {
        this.habilitada = habilitada;
    }
}
