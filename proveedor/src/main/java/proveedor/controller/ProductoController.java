package proveedor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proveedor.service.ProductoService;
import proveedor.dto.ProductoDTO;
import proveedor.models.Producto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> productos = productoService.obtenerProductos();
        return ResponseEntity.ok(productos);
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarStock(@RequestBody Map<String, Object> data) {
        String productoId = String.valueOf(data.get("producto_id"));
        Integer nuevaCantidad = Integer.valueOf(data.get("nueva_cantidad").toString());

        if (productoId == null || nuevaCantidad == null) {
            return ResponseEntity.badRequest().body("Faltan datos necesarios");
        }

        try {
            productoService.modificarStock(productoId, nuevaCantidad);
            return ResponseEntity.ok("Stock modificado y órdenes reprocesadas");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al modificar el stock: " + e.getMessage());
        }
    }

    @PostMapping("/alta")
    public ResponseEntity<?> altaProducto(@RequestBody ProductoDTO productoDTO) {
        System.out.println("Alta de producto: " + productoDTO);
        try {
            Producto producto = productoService.altaProducto(productoDTO);
            return ResponseEntity.status(201).body("Producto creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear el producto: " + e.getMessage());
        }
    }
}

