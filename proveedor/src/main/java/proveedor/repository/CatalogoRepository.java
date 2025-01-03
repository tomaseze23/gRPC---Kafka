package proveedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proveedor.models.Catalogo;

@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
}

