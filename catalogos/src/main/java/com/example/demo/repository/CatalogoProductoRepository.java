package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.CatalogoProducto;

@Repository
public interface CatalogoProductoRepository extends JpaRepository<CatalogoProducto, Long> {
}

