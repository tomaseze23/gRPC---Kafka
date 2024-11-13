// src/main/java/com/example/demo/controller/OrdenCompraCentralController.java
package com.example.demo.controller;

import com.example.demo.dto.OrdenCompraCentralDTO;
import com.example.demo.service.OrdenCompraCentralService;
import com.example.demo.wsdl.ordencompracentral.OrdenCompraCentral;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ordencompra-central")
@Tag(name = "Orden Compra Central", description = "Operaciones relacionadas con las órdenes de compra central")
public class OrdenCompraCentralController {

    @Autowired
    private OrdenCompraCentralService ordenCompraCentralService;

    // Obtener todas las órdenes de compra central
    @Operation(summary = "Obtener todas las órdenes de compra central", description = "Recupera una lista de todas las órdenes de compra central disponibles")
    @GetMapping("/todas")
    public ResponseEntity<List<OrdenCompraCentralDTO>> obtenerTodasLasOrdenesCompraCentral() {
        List<OrdenCompraCentral> ordenesCompraCentral = ordenCompraCentralService.obtenerTodasLasOrdenesCompraCentral();
        List<OrdenCompraCentralDTO> ordenesCompraCentralDTO = ordenesCompraCentral.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ordenesCompraCentralDTO);
    }

    // Obtener una orden de compra central por ID
    @Operation(summary = "Obtener una orden de compra central por ID", description = "Recupera una orden de compra central específica utilizando su ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraCentralDTO> obtenerOrdenCompraCentralPorId(@PathVariable long id) {
        OrdenCompraCentral ordenCompraCentral = ordenCompraCentralService.obtenerOrdenCompraCentralPorId(id);
        if (ordenCompraCentral != null) {
            OrdenCompraCentralDTO ordenCompraCentralDTO = mapEntityToDto(ordenCompraCentral);
            return ResponseEntity.ok(ordenCompraCentralDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear una nueva orden de compra central
    @Operation(summary = "Crear una nueva orden de compra central", description = "Permite crear una nueva orden de compra central")
    @PostMapping("/crear")
    public ResponseEntity<OrdenCompraCentralDTO> crearOrdenCompraCentral(@RequestBody @Valid OrdenCompraCentralDTO ordenCompraCentralDTO) {
        OrdenCompraCentral ordenCompraCentral = mapDtoToEntity(ordenCompraCentralDTO);
        OrdenCompraCentral nuevaOrden = ordenCompraCentralService.crearOrdenCompraCentral(ordenCompraCentral);
        OrdenCompraCentralDTO respuestaDTO = mapEntityToDto(nuevaOrden);
        return ResponseEntity.ok(respuestaDTO);
    }

    // Métodos de mapeo entre Entidad y DTO
    private OrdenCompraCentralDTO mapEntityToDto(OrdenCompraCentral entity) {
        OrdenCompraCentralDTO dto = new OrdenCompraCentralDTO();
        dto.setId(entity.getId());
        dto.setTiendaId(entity.getTiendaId());
        dto.setCodigoArticulo(getCodigoArticulo());
        dto.setColor(entity.getColor());
        dto.setTalle(entity.getTalle());
        dto.setCantidadSolicitada(getCantidadSolicitada());
        return dto;
    }

    private OrdenCompraCentral mapDtoToEntity(OrdenCompraCentralDTO dto) {
        OrdenCompraCentral entity = new OrdenCompraCentral();
        entity.setTiendaId(dto.getTiendaId());
        entity.setCodigoArticulo(dto.getCodigoArticulo());
        entity.setColor(dto.getColor());
        entity.setTalle(dto.getTalle());
        entity.setCantidadSolicitada(dto.getCantidadSolicitada().toString());
        return entity;
    }

    private String getCodigoArticulo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Integer getCantidadSolicitada() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
