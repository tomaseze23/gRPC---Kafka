// src/main/java/com/example/demo/services/UsuarioService.java
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.clientes.UsuarioCliente;
import com.example.demo.wsdl.usuario.CreateUsuarioResponse;
import com.example.demo.wsdl.usuario.GetAllUsuariosResponse;
import com.example.demo.wsdl.usuario.GetUsuarioResponse;
import com.example.demo.wsdl.usuario.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioCliente usuarioCliente;

    /**
     * Obtener un usuario por nombre de usuario
     */
    public GetUsuarioResponse obtenerUsuario(String nombreUsuario) {
        return usuarioCliente.getUsuario(nombreUsuario);
    }

    /**
     * Obtener todos los usuarios
     */
    public GetAllUsuariosResponse obtenerTodosUsuarios() {
        return usuarioCliente.getAllUsuarios();
    }

    /**
     * Crear un nuevo usuario
     */
    public CreateUsuarioResponse crearUsuario(Usuario usuario) {
        return usuarioCliente.createUsuario(usuario);
    }
}
