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
}
