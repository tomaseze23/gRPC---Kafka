// src/main/java/com/example/demo/controller/CatalogoProductoController.java
package com.example.demo.controller;

import com.example.demo.dto.CatalogoProductoDTO;
import com.example.demo.service.CatalogoProductoService;
import com.example.demo.util.DateConverter;
import com.example.demo.wsdl.catalogoproducto.CatalogoProducto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalogoproducto")
@Tag(name = "Catálogo Producto", description = "Operaciones relacionadas con el catálogo de productos")
public class CatalogoProductoController {

    @Autowired
    private CatalogoProductoService catalogoProductoService;

    @Operation(summary = "Crear un nuevo catálogo de producto", description = "Crea un nuevo catálogo de producto enviando una solicitud al servicio SOAP")
    @PostMapping("/crear")
    public ResponseEntity<Long> crearCatalogoProducto(@RequestBody CatalogoProductoDTO catalogoProductoDTO) {
        CatalogoProducto catalogoProducto = new CatalogoProducto();
        catalogoProducto.setId(catalogoProductoDTO.getId());
        catalogoProducto.setCatalogoId(catalogoProductoDTO.getCatalogoId());
        catalogoProducto.setNombreProducto(catalogoProductoDTO.getNombreProducto());
     
        long catalogoProductoId = catalogoProductoService.crearCatalogoProducto(catalogoProducto);
        return ResponseEntity.ok(catalogoProductoId);
    }

    @Operation(summary = "Obtener un catálogo de producto por ID", description = "Obtiene un catálogo de producto específico utilizando su ID")
    @GetMapping("/{id}")
    public ResponseEntity<CatalogoProductoDTO> getCatalogoProducto(@PathVariable long id) {
        CatalogoProducto catalogoProducto = catalogoProductoService.getCatalogoProducto(id);
        if (catalogoProducto != null) {
            CatalogoProductoDTO catalogoProductoDTO = new CatalogoProductoDTO();
            catalogoProductoDTO.setId(catalogoProducto.getId());
            catalogoProductoDTO.setCatalogoId(catalogoProducto.getCatalogoId());
            catalogoProductoDTO.setNombreProducto(catalogoProducto.getNombreProducto());
            return ResponseEntity.ok(catalogoProductoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener todos los catálogos de productos por ID de catálogo", description = "Obtiene una lista de todos los catálogos de productos asociados a un catálogo específico")
    @GetMapping("/catalogo/{catalogoId}")
    public ResponseEntity<List<CatalogoProductoDTO>> getAllCatalogoProductoByCatalogo(@PathVariable long catalogoId) {
        List<CatalogoProducto> catalogosProductos = catalogoProductoService.getAllCatalogoProductoByCatalogo(catalogoId);
        List<CatalogoProductoDTO> catalogoProductoDTOs = catalogosProductos.stream().map(catalogoProducto -> {
            CatalogoProductoDTO dto = new CatalogoProductoDTO();
            dto.setId(catalogoProducto.getId());
            dto.setCatalogoId(catalogoProducto.getCatalogoId());
            dto.setNombreProducto(catalogoProducto.getNombreProducto());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(catalogoProductoDTOs);
    }

    @Operation(summary = "Obtener todos los catálogos de productos por ID de tienda", description = "Obtiene una lista de todos los catálogos de productos asociados a una tienda específica")
    @GetMapping("/tienda/{tiendaId}")
    public ResponseEntity<List<CatalogoProductoDTO>> getAllCatalogoProductoByTienda(@PathVariable String tiendaId) {
        List<CatalogoProducto> catalogosProductos = catalogoProductoService.getAllCatalogoProductoByTienda(tiendaId);
        List<CatalogoProductoDTO> catalogoProductoDTOs = catalogosProductos.stream().map(catalogoProducto -> {
            CatalogoProductoDTO dto = new CatalogoProductoDTO();
            dto.setId(catalogoProducto.getId());
            dto.setCatalogoId(catalogoProducto.getCatalogoId());
            dto.setNombreProducto(catalogoProducto.getNombreProducto());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(catalogoProductoDTOs);
    }

    @Operation(summary = "Actualizar un catálogo de producto", description = "Actualiza un catálogo de producto existente enviando una solicitud al servicio SOAP")
    @PutMapping("/actualizar")
    public ResponseEntity<Boolean> updateCatalogoProducto(@RequestBody CatalogoProductoDTO catalogoProductoDTO) {
        CatalogoProducto catalogoProducto = new CatalogoProducto();
        catalogoProducto.setId(catalogoProductoDTO.getId());
        catalogoProducto.setCatalogoId(catalogoProductoDTO.getCatalogoId());
        catalogoProducto.setNombreProducto(catalogoProductoDTO.getNombreProducto());

        boolean success = catalogoProductoService.updateCatalogoProducto(catalogoProducto);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "Eliminar un catálogo de producto", description = "Elimina un catálogo de producto específico utilizando su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCatalogoProducto(@PathVariable long id) {
        boolean success = catalogoProductoService.deleteCatalogoProducto(id);
        return ResponseEntity.ok(success);
    }
}
