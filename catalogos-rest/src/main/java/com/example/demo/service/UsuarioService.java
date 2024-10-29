package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.clientes.UsuarioCliente;
import com.example.demo.dto.UsuarioDTO;
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
        System.out.println("PASAMOS POR ACA");
        return usuarioCliente.createUsuario(usuario);
    }

    public Map<String, Object> procesarCargaMasiva(List<UsuarioDTO> usuariosDTO) {
        List<Map<String, Object>> errores = new ArrayList<>();
        List<Usuario> usuariosParaCrear = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        int numeroLinea = 1;

        for (UsuarioDTO usuarioDTO : usuariosDTO) {
            System.out.println("Usuario " + usuarioDTO);

            List<String> erroresUsuario = new ArrayList<>();

            if (tieneCamposVacios(usuarioDTO)) {
                erroresUsuario.add("Campos vacíos o incompletos.");
            }

            if (esUsuarioDuplicado(usuarioDTO.getNombreUsuario())) {
                erroresUsuario.add("Usuario duplicado.");
            }

            if (!existeTienda(usuarioDTO.getTiendaId())) {
                erroresUsuario.add("Código de tienda no existe.");
            }

            else if (!tiendaHabilitada(usuarioDTO.getTiendaId())) {
                erroresUsuario.add("Tienda deshabilitada.");
            }

            if (!erroresUsuario.isEmpty()) {
                Map<String, Object> errorDetalle = new HashMap<>();
                errorDetalle.put("line", numeroLinea);
                errorDetalle.put("message", String.join(" ", erroresUsuario));
                errores.add(errorDetalle);
            } else {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
                nuevoUsuario.setContrasena(usuarioDTO.getContrasena());
                nuevoUsuario.setNombre(usuarioDTO.getNombre());
                nuevoUsuario.setApellido(usuarioDTO.getApellido());
                nuevoUsuario.setTiendaId(usuarioDTO.getTiendaId());

                usuariosParaCrear.add(nuevoUsuario);
            }

            numeroLinea++;
        }

        response.put("errors", errores);

        if (!usuariosParaCrear.isEmpty()) {
            for (Usuario usuario : usuariosParaCrear) {
                crearUsuario(usuario);
            }
            response.put("message", "Usuarios válidos creados exitosamente.");
        } else {
            response.put("message", "No se creó ningún usuario.");
        }

        return response;
    }

    private boolean tieneCamposVacios(UsuarioDTO usuarioDTO) {
        return usuarioDTO.getNombreUsuario() == null || usuarioDTO.getNombreUsuario().trim().isEmpty()
                || usuarioDTO.getContrasena() == null || usuarioDTO.getContrasena().trim().isEmpty()
                || usuarioDTO.getNombre() == null || usuarioDTO.getNombre().trim().isEmpty()
                || usuarioDTO.getApellido() == null || usuarioDTO.getApellido().trim().isEmpty()
                || (usuarioDTO.getTiendaId() != null && usuarioDTO.getTiendaId().trim().isEmpty());
    }

    private boolean esUsuarioDuplicado(String nombreUsuario) {
        GetUsuarioResponse response = obtenerUsuario(nombreUsuario);
        return response != null && response.getUsuario() != null;
    }

    private boolean existeTienda(String codigoTienda) {
        Tienda tienda = tiendaService.getTiendaByCodigo(codigoTienda);
        return tienda != null;
    }

    private boolean tiendaHabilitada(String codigoTienda) {
        Tienda tienda = tiendaService.getTiendaByCodigo(codigoTienda);
        return tienda != null && tienda.isHabilitada();
    }

}
