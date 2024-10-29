package proveedor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proveedor.service.KafkaProducerService;
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

    @Autowired
    private KafkaProducerService kafkaProducerService;

    // GET /productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> productos = productoService.obtenerProductos();
        return ResponseEntity.ok(productos);
    }

    // PUT /productos/modificar
    @PutMapping("/modificar")
    public ResponseEntity<?> modificarStock(@RequestBody Map<String, Object> data) {
        Long productoId = Long.valueOf(data.get("producto_id").toString());
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

    // POST /productos/alta
    @PostMapping("/alta")
    public ResponseEntity<?> altaProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            Producto producto = productoService.altaProducto(productoDTO);
            return ResponseEntity.status(201).body("Producto creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear el producto: " + e.getMessage());
        }
    }
}

