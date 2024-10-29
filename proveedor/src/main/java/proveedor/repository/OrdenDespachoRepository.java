package proveedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proveedor.models.OrdenDespacho;

@Repository
public interface OrdenDespachoRepository extends JpaRepository<OrdenDespacho, Long> {
}
