// src/main/java/com/example/demo/controller/ProductoController.java
package com.example.demo.controller;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.service.ProductoService;
import com.example.demo.wsdl.producto.Producto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/producto")
@Tag(name = "Producto", description = "Operaciones relacionadas con productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener un Producto por Código
    @Operation(summary = "Obtener un producto por código", description = "Obtiene un producto específico utilizando su código")
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<ProductoDTO> getProductoByCodigo(@PathVariable String codigo) {
        Producto producto = productoService.getProductoByCodigo(codigo);
        if (producto != null) {
            ProductoDTO productoDTO = mapEntityToDto(producto);
            return ResponseEntity.ok(productoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener todos los Productos
    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos disponibles")
    @GetMapping("/todos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<Producto> productos = productoService.getAllProductos();
        if (productos != null && !productos.isEmpty()) {
            List<ProductoDTO> productoDTOs = productos.stream()
                    .map(this::mapEntityToDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(productoDTOs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // Métodos de mapeo entre DTO y Entidad
    private ProductoDTO mapEntityToDto(Producto entity) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setCodigo(entity.getCodigo());
        dto.setTalle(entity.getTalle());
        dto.setFoto(entity.getFoto());
        dto.setColor(entity.getColor());
        dto.setCantidadStockProveedor(entity.getCantidadStockProveedor());
        return dto;
    }

    private Producto mapDtoToEntity(ProductoDTO dto) {
        Producto entity = new Producto();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setCodigo(dto.getCodigo());
        entity.setTalle(dto.getTalle());
        entity.setFoto(dto.getFoto());
        entity.setColor(dto.getColor());
        entity.setCantidadStockProveedor(dto.getCantidadStockProveedor());
        return entity;
    }
}
