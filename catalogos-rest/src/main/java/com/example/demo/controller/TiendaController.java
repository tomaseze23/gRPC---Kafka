// src/main/java/com/example/demo/controller/TiendaController.java
package com.example.demo.controller;

import com.example.demo.dto.TiendaDTO;
import com.example.demo.service.TiendaService;
import com.example.demo.wsdl.tienda.Tienda;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tienda")
@Tag(name = "Tienda", description = "Operaciones relacionadas con tiendas")
public class TiendaController {

    @Autowired
    private TiendaService tiendaService;

    // Obtener una Tienda por Código
    @Operation(summary = "Obtener una tienda por código", description = "Obtiene una tienda específica utilizando su código")
    @GetMapping("/{codigo}")
    public ResponseEntity<TiendaDTO> getTiendaByCodigo(@PathVariable String codigo) {
        Tienda tienda = tiendaService.getTiendaByCodigo(codigo);
        if (tienda != null) {
            TiendaDTO tiendaDTO = mapEntityToDto(tienda);
            return ResponseEntity.ok(tiendaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Métodos de mapeo entre DTO y Entidad
    private TiendaDTO mapEntityToDto(Tienda entity) {
        TiendaDTO dto = new TiendaDTO();
        dto.setCodigo(entity.getCodigo());
        dto.setDireccion(entity.getDireccion());
        dto.setCiudad(entity.getCiudad());
        dto.setProvincia(entity.getProvincia());
        dto.setHabilitada(entity.isHabilitada());
        return dto;
    }

    @Operation(summary = "Obtener todas las tiendas", description = "Obtiene una lista de todas las tiendas")
    @GetMapping
    public ResponseEntity<List<TiendaDTO>> getAllTiendas() {
        List<Tienda> tiendas = tiendaService.getAllTiendas();
        if (tiendas != null && !tiendas.isEmpty()) {
            List<TiendaDTO> tiendaDTOs = tiendas.stream()
                    .map(this::mapEntityToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(tiendaDTOs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    private Tienda mapDtoToEntity(TiendaDTO dto) {
        Tienda entity = new Tienda();
        entity.setCodigo(dto.getCodigo());
        entity.setDireccion(dto.getDireccion());
        entity.setCiudad(dto.getCiudad());
        entity.setProvincia(dto.getProvincia());
        entity.setHabilitada(dto.getHabilitada());
        return entity;
    }
}
