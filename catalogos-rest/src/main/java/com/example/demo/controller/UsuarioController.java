package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.wsdl.usuario.GetAllUsuariosResponse;
import com.example.demo.wsdl.usuario.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {

    @Autowired
    private com.example.demo.service.UsuarioService usuarioService;

    /**
     * Endpoint para crear un nuevo usuario
     * POST /api/usuarios/crear
     */
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario enviando una solicitud al servicio SOAP")
    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = mapDtoToUsuario(usuarioDTO);
        String usuarioId = usuarioService.crearUsuario(usuario).getMensaje();
        return new ResponseEntity<>(usuarioId, HttpStatus.CREATED);
    }

    /**
     * Endpoint para obtener un usuario por nombre de usuario
     * GET /api/usuarios/{nombreUsuario}
     */
    @Operation(summary = "Obtener un usuario por nombre de usuario", description = "Obtiene un usuario específico utilizando su nombre de usuario")
    @GetMapping("/{nombreUsuario}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable String nombreUsuario) {
        Usuario usuario = usuarioService.obtenerUsuario(nombreUsuario).getUsuario();
        if (usuario != null) {
            UsuarioDTO usuarioDTO = mapUsuarioToDto(usuario);
            usuarioDTO.setContrasena(null); // No exponer la contraseña
            return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para obtener todos los usuarios
     * GET /api/usuarios
     */
    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene una lista de todos los usuarios disponibles")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        GetAllUsuariosResponse usuarios = usuarioService.obtenerTodosUsuarios();
        List<UsuarioDTO> usuarioDTOs = usuarios.getUsuarios().getUsuario().stream()
                .map(this::mapUsuarioToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<List<UsuarioDTO>>(usuarioDTOs, HttpStatus.OK);
    }

    /**
     * Método auxiliar para mapear UsuarioDTO a Usuario
     */
    private Usuario mapDtoToUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setContrasena(dto.getContrasena()); // Asegúrate de hashear la contraseña antes de enviar
        usuario.setTiendaId(dto.getTiendaId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setHabilitado(dto.getHabilitado() != null ? dto.getHabilitado() : true);
        return usuario;
    }

    /**
     * Método auxiliar para mapear Usuario a UsuarioDTO
     */
    private UsuarioDTO mapUsuarioToDto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setContrasena(usuario.getContrasena());
        dto.setTiendaId(usuario.getTiendaId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setHabilitado(usuario.isHabilitado());
        return dto;
    }

    @PostMapping("/carga-masiva")
    public ResponseEntity<Map<String, Object>> cargaMasivaUsuarios(@RequestBody List<UsuarioDTO> usuariosDTO) {
        System.out.println("Recibidos: " + usuariosDTO);
        Map<String, Object> result = usuarioService.procesarCargaMasiva(usuariosDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    

}
