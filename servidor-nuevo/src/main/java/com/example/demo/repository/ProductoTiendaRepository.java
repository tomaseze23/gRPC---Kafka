package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ProductoTienda;

@Repository
public interface ProductoTiendaRepository extends JpaRepository<ProductoTienda, Long> {

}
