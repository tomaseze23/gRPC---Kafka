package proveedor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proveedor.models.CatalogoProducto;

@Repository
public interface CatalogoProductoRepository extends JpaRepository<CatalogoProducto, Long> {

    List<CatalogoProducto> findByCatalogoId(Long catalogoId);

    List<CatalogoProducto> findByCatalogoTiendaCodigo(String tiendaCodigo);

}
