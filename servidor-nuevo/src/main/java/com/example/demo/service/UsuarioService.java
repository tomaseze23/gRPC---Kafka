package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityManager;

@Service
public class UsuarioService extends UsuarioServiceGrpc.UsuarioServiceImplBase {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void createUsuario(com.example.demo.service.Usuario request,
            StreamObserver<com.example.demo.service.Usuario> responseObserver) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setContrasena(request.getContrasena());
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setHabilitado(request.getHabilitado());

        Usuario savedUsuario = usuarioRepository.save(usuario);

        com.example.demo.service.Usuario response = com.example.demo.service.Usuario.newBuilder()
                .setId(savedUsuario.getId())
                .setNombreUsuario(savedUsuario.getNombreUsuario())
                .setContrasena(savedUsuario.getContrasena())
                .setNombre(savedUsuario.getNombre())
                .setApellido(savedUsuario.getApellido())
                .setHabilitado(savedUsuario.isHabilitado())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUsuario(com.example.demo.service.Usuario request,
            StreamObserver<com.example.demo.service.Usuario> responseObserver) {
        usuarioRepository.findById(request.getId()).ifPresentOrElse(usuario -> {
            com.example.demo.service.Usuario response = com.example.demo.service.Usuario.newBuilder()
                    .setId(usuario.getId())
                    .setNombreUsuario(usuario.getNombreUsuario())
                    .setContrasena(usuario.getContrasena())
                    .setNombre(usuario.getNombre())
                    .setApellido(usuario.getApellido())
                    .setHabilitado(usuario.isHabilitado())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            responseObserver.onError(new RuntimeException("Usuario no encontrado"));
        });
    }

    @Override
    public void listUsuarios(ListUsuariosRequest request, StreamObserver<UsuarioList> responseObserver) {

        List<Usuario> usuarios = entityManager.createQuery(
                "SELECT u FROM Usuario u LEFT JOIN FETCH u.tienda", Usuario.class)
                .getResultList();


        UsuarioList response = UsuarioList.newBuilder()
                .addAllUsuarios(usuarios.stream().map(usuario -> com.example.demo.service.Usuario.newBuilder()
                        .setId(usuario.getId())
                        .setNombreUsuario(usuario.getNombreUsuario())
                        .setContrasena(usuario.getContrasena())
                        .setNombre(usuario.getNombre())
                        .setApellido(usuario.getApellido())
                        .setHabilitado(usuario.isHabilitado())
                        .setTiendaId(usuario.getTienda().getCodigo())
                        .build()).collect(Collectors.toList()))
                .build();

        System.out.println("response: " + response);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {

        usuarioRepository.findByNombreUsuario(request.getNombreUsuario()).ifPresentOrElse(usuario -> {
            boolean success = usuario.getContrasena().equals(request.getContrasena());

            LoginResponse response = LoginResponse.newBuilder()
                    .setSuccess(success)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            responseObserver.onError(new RuntimeException("Usuario no encontrado"));
        });
    }

    @Override
    public void updateUsuario(com.example.demo.service.Usuario request,
            StreamObserver<com.example.demo.service.Usuario> responseObserver) {
    
        System.out.println("updateUsuario: " + request);
    
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(request.getId());
    
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
    
            usuario.setNombreUsuario(request.getNombreUsuario());
            usuario.setContrasena(request.getContrasena());
            usuario.setNombre(request.getNombre());
            usuario.setApellido(request.getApellido());
            usuario.setHabilitado(request.getHabilitado());
    
            usuarioRepository.save(usuario);
    
            com.example.demo.service.Usuario response = com.example.demo.service.Usuario.newBuilder()
                    .setId(usuario.getId())
                    .setNombreUsuario(usuario.getNombreUsuario())
                    .setContrasena(usuario.getContrasena())
                    .setNombre(usuario.getNombre())
                    .setApellido(usuario.getApellido())
                    .setHabilitado(usuario.isHabilitado())
                    .build();
    
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new RuntimeException("Usuario no encontrado"));
        }
    }
    


}
