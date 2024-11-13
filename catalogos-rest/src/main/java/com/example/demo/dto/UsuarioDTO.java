// src/main/java/com/example/demo/dto/UsuarioDTO.java
package com.example.demo.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String nombreUsuario;
    private String contrasena;
    private String tiendaId;
    private String nombre;
    private String apellido;
    private Boolean habilitado;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(String tiendaId) {
        this.tiendaId = tiendaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
