package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.models.Tienda;
import com.example.demo.models.Usuario;
import com.example.demo.repository.TiendaRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.usuarios.CreateUsuarioRequest;
import com.example.usuarios.CreateUsuarioResponse;
import com.example.usuarios.GetAllUsuariosRequest;
import com.example.usuarios.GetAllUsuariosResponse;
import com.example.usuarios.GetUsuarioRequest;
import com.example.usuarios.GetUsuarioResponse;
import com.example.usuarios.Usuarios; // Clase que contiene una lista de Usuario

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class UsuarioEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/usuarios";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    /**
     * Operación para obtener un usuario específico por nombre de usuario.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsuarioRequest")
    @ResponsePayload
    public GetUsuarioResponse getUsuario(@RequestPayload GetUsuarioRequest request) {
        GetUsuarioResponse response = new GetUsuarioResponse();
        Usuario usuario = usuarioRepository.findByNombreUsuario(request.getNombreUsuario()).orElse(null);

        if (usuario != null) {
            com.example.usuarios.Usuario usuarioResponse = mapUsuarioToResponse(usuario);
            response.setUsuario(usuarioResponse);
        }

        return response;
    }

    /**
     * Operación para obtener todos los usuarios.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsuariosRequest")
    @ResponsePayload
    public GetAllUsuariosResponse getAllUsuarios(@RequestPayload GetAllUsuariosRequest request) {
        GetAllUsuariosResponse response = new GetAllUsuariosResponse();
        List<Usuario> usuarios = usuarioRepository.findAll();

        Usuarios usuariosResponse = new Usuarios();
        List<com.example.usuarios.Usuario> listaUsuarios = usuarios.stream()
                .map(this::mapUsuarioToResponse)
                .collect(Collectors.toList());

        usuariosResponse.getUsuario().addAll(listaUsuarios);
        response.setUsuarios(usuariosResponse);

        return response;
    }

    /**
     * Operación para crear un nuevo usuario.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUsuarioRequest")
    @ResponsePayload
    public CreateUsuarioResponse createUsuario(@RequestPayload CreateUsuarioRequest request) {
        CreateUsuarioResponse response = new CreateUsuarioResponse();

        System.out.println("PASO POR ACAAAAAAAAAAAAAAAAAAAAAAAAA");

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getUsuario().getNombreUsuario());
        usuario.setContrasena(request.getUsuario().getContrasena());
        usuario.setNombre(request.getUsuario().getNombre());
        usuario.setApellido(request.getUsuario().getApellido());
        usuario.setHabilitado(true);

        if (request.getUsuario().getTiendaId() != null && !request.getUsuario().getTiendaId().isEmpty()) {
            Tienda tienda = tiendaRepository.findByCodigo(request.getUsuario().getTiendaId()).orElse(null);
            usuario.setTienda(tienda);
        }

        Usuario usuarioCreado = usuarioRepository.save(usuario);

        com.example.usuarios.Usuario usuarioResponse = mapUsuarioToResponse(usuarioCreado);
        response.setUsuarioCreado(usuarioResponse);
        response.setMensaje("Usuario creado exitosamente.");

        return response;
    }

    /**
     * Método auxiliar para mapear la entidad Usuario a la clase de respuesta generada.
     */
    private com.example.usuarios.Usuario mapUsuarioToResponse(Usuario usuario) {
        com.example.usuarios.Usuario usuarioResponse = new com.example.usuarios.Usuario();
        usuarioResponse.setNombreUsuario(usuario.getNombreUsuario());
        usuarioResponse.setContrasena(usuario.getContrasena());
        usuarioResponse.setNombre(usuario.getNombre());
        usuarioResponse.setApellido(usuario.getApellido());
        usuarioResponse.setHabilitado(true);

        if (usuario.getTienda() != null) {
            Tienda tiendaResponse = new Tienda();
            tiendaResponse.setCodigo(usuario.getTienda().getCodigo());
            tiendaResponse.setDireccion(usuario.getTienda().getDireccion());
            tiendaResponse.setCiudad(usuario.getTienda().getCiudad());
            tiendaResponse.setProvincia(usuario.getTienda().getProvincia());
            tiendaResponse.setHabilitada(usuario.getTienda().isHabilitada());
            usuarioResponse.setTiendaId(tiendaResponse.getCodigo());
        }

        return usuarioResponse;
    }
}
