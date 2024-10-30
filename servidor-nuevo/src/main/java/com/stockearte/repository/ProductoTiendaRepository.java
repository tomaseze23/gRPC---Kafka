package com.stockearte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockearte.model.ProductoTienda;

@Repository
public interface ProductoTiendaRepository extends JpaRepository<ProductoTienda, Long> {

}
