package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, String> {

	Optional<Tienda> findByCodigo(String codigo);
}
