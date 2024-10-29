package proveedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import proveedor.models.ItemOrdenCompra;
import java.util.List;

@Repository
public interface ItemOrdenCompraRepository extends JpaRepository<ItemOrdenCompra, Long> {
    List<ItemOrdenCompra> findByOrdenCompraId(Long ordenCompraId);
}
