// src/main/java/com/example/demo/services/UsuarioService.java
package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.clientes.UsuarioCliente;
import com.example.demo.wsdl.tienda.Tienda;
import com.example.demo.wsdl.usuario.CreateUsuarioResponse;
import com.example.demo.wsdl.usuario.GetAllUsuariosResponse;
import com.example.demo.wsdl.usuario.GetUsuarioResponse;
import com.example.demo.wsdl.usuario.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioCliente usuarioCliente;

    @Autowired
    private TiendaService tiendaService;

    public GetUsuarioResponse obtenerUsuario(String nombreUsuario) {
        return usuarioCliente.getUsuario(nombreUsuario);
    }

  
    public GetAllUsuariosResponse obtenerTodosUsuarios() {
        return usuarioCliente.getAllUsuarios();
    }

   
    public CreateUsuarioResponse crearUsuario(Usuario usuario) {
        return usuarioCliente.createUsuario(usuario);
    }

    public Map<String, Object> procesarCargaMasiva(MultipartFile file) {
        List<String> errores = new ArrayList<>();
        List<Usuario> usuariosParaCrear = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linea;
            int numeroLinea = 1;

            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");

                if (campos.length < 5 || tieneCamposVacios(campos)) {
                    errores.add("Línea " + numeroLinea + ": Campos vacíos o incompletos.");
                    continue;
                }

                String usuario = campos[0];
                String contrasena = campos[1];
                String nombre = campos[2];
                String apellido = campos[3];
                String codigoTienda = campos[4];

                if (esUsuarioDuplicado(usuario)) {
                    errores.add("Línea " + numeroLinea + ": Usuario duplicado.");
                    continue;
                }

                if (!existeTienda(codigoTienda)) {
                    errores.add("Línea " + numeroLinea + ": Código de tienda no existe.");
                    continue;
                }

                if (!tiendaHabilitada(codigoTienda)) {
                    errores.add("Línea " + numeroLinea + ": Tienda deshabilitada.");
                    continue;
                }

                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombreUsuario(usuario);
                nuevoUsuario.setContrasena(contrasena); // Hash según sea necesario
                nuevoUsuario.setNombre(nombre);
                nuevoUsuario.setApellido(apellido);
                nuevoUsuario.setTiendaId(codigoTienda);

                usuariosParaCrear.add(nuevoUsuario);
                numeroLinea++;
            }

            response.put("errores", errores);

            if (!usuariosParaCrear.isEmpty()) {
                for (Usuario usuario : usuariosParaCrear) {
                    crearUsuario(usuario);
                }
                response.put("mensaje", "Usuarios válidos creados exitosamente.");
            } else {
                response.put("mensaje", "No se creó ningún usuario.");
            }

        } catch (Exception e) {
            response.put("mensaje", "Error al procesar el archivo CSV.");
            response.put("errores", List.of(e.getMessage()));
        }

        return response;
    }

    private boolean tieneCamposVacios(String[] campos) {
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean esUsuarioDuplicado(String nombreUsuario) {
        GetUsuarioResponse response = obtenerUsuario(nombreUsuario);
        return response != null && response.getUsuario() != null;
    }

    private boolean existeTienda(String codigoTienda) {
        Tienda response = tiendaService.getTiendaByCodigo(codigoTienda);
        return response != null;
    }

    private boolean tiendaHabilitada(String codigoTienda) {
        Tienda response = tiendaService.getTiendaByCodigo(codigoTienda);
        if (response != null) {
            return response.isHabilitada();
        }
        return false;
    }
}
