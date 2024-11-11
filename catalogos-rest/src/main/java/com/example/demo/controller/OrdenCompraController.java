// src/main/java/com/example/demo/controller/OrdenCompraController.java
package com.example.demo.controller;

import com.example.demo.dto.OrdenCompraDTO;
import com.example.demo.service.OrdenCompraService;
import com.example.demo.wsdl.ordencompra.OrdenCompra;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordencompra")
@Tag(name = "Orden Compra", description = "Operaciones relacionadas con las órdenes de compra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    // Obtener todas las órdenes de compra
    @Operation(summary = "Obtener todas las órdenes de compra", description = "Recupera una lista de todas las órdenes de compra disponibles")
    @GetMapping("/todas")
    public ResponseEntity<List<OrdenCompraDTO>> obtenerTodasLasOrdenesCompra() {
        List<OrdenCompra> ordenesCompra = ordenCompraService.obtenerTodasLasOrdenesCompra();
        List<OrdenCompraDTO> ordenesCompraDTO = ordenesCompra.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ordenesCompraDTO);
    }

    // Obtener una orden de compra por ID
    @Operation(summary = "Obtener una orden de compra por ID", description = "Recupera una orden de compra específica utilizando su ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraDTO> obtenerOrdenCompraPorId(@PathVariable long id) {
        OrdenCompra ordenCompra = ordenCompraService.obtenerOrdenCompraPorId(id);
        if (ordenCompra != null) {
            OrdenCompraDTO ordenCompraDTO = mapEntityToDto(ordenCompra);
            return ResponseEntity.ok(ordenCompraDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Métodos de mapeo entre Entidad y DTO
    private OrdenCompraDTO mapEntityToDto(OrdenCompra entity) {
        OrdenCompraDTO dto = new OrdenCompraDTO();
        dto.setId(entity.getId());
        dto.setTiendaId(entity.getTiendaId());
        dto.setFechaSolicitud(entity.getFechaSolicitud() != null ? entity.getFechaSolicitud().toGregorianCalendar().toZonedDateTime().toLocalDateTime() : null);
        dto.setFechaEnvio(entity.getFechaEnvio() != null ? entity.getFechaEnvio().toGregorianCalendar().toZonedDateTime().toLocalDateTime() : null);
        dto.setFechaRecepcion(entity.getFechaRecepcion() != null ? entity.getFechaRecepcion().toGregorianCalendar().toZonedDateTime().toLocalDateTime() : null);
        dto.setEstado(entity.getEstado());
        dto.setObservaciones(entity.getObservaciones());
        return dto;
    }
}
