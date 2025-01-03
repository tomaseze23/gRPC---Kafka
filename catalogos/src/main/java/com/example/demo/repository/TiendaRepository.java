package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, String> {

    Optional<Tienda> findByCodigo(String codigo);
}
