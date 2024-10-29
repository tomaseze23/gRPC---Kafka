package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.CatalogoProducto;

@Repository
public interface CatalogoProductoRepository extends JpaRepository<CatalogoProducto, Long> {

    List<CatalogoProducto> findByCatalogoId(Long catalogoId);

    List<CatalogoProducto> findByCatalogoTiendaCodigo(String tiendaCodigo);

    Optional<CatalogoProducto> findByProductoCodigo(String codigo);

}
