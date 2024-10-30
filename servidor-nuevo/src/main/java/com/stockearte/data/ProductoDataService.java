package com.stockearte.data;

import com.stockearte.repository.ProductoRepository;

import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductoDataService {
    private final ProductoRepository productoRepository;
    private final EntityManager entityManager;

    public ProductoDataService(ProductoRepository productoRepository, EntityManager entityManager) {
        this.productoRepository = productoRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public void deleteProductoById(Long id) {
        com.stockearte.model.Producto producto = entityManager.find(com.stockearte.model.Producto.class, id);

        if (producto != null) {
            entityManager.remove(producto);
        } else {
            throw new RuntimeException("Producto no encontrado");
        }
    }
}
