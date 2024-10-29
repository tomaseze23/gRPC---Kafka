package proveedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proveedor.models.Tienda;
import java.util.Optional;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, String> {
    Optional<Tienda> findByCodigo(String codigo);
}
