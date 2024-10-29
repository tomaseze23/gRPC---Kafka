package proveedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import proveedor.models.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}

