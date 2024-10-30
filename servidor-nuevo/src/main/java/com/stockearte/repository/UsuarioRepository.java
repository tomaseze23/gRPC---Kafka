package com.stockearte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockearte.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
       Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
