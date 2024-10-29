package proveedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import proveedor.models.OrdenCompra;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    @Query("SELECT DISTINCT oc FROM OrdenCompra oc, ItemOrdenCompra i WHERE oc.id = i.ordenCompra.id AND oc.estado = 'PAUSADA' AND i.producto.id = :productoId")
    List<OrdenCompra> findOrdenesPausadasPorProducto(@Param("productoId") Long productoId);
}

