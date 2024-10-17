package proveedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proveedor.models.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, String> {
}
