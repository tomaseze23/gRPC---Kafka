package proveedor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proveedor.models.OrdenCompra;
import proveedor.service.ModificarEstadoService;
import proveedor.service.OrdenCompraService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordenes_compra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenDeCompraService;

    @Autowired
    private ModificarEstadoService modificarEstadoService;

    // GET /ordenes_compra
    @GetMapping
    public ResponseEntity<List<OrdenCompra>> obtenerOrdenesCompra() {
        try {
            List<OrdenCompra> ordenesCompra = ordenDeCompraService.obtenerOrdenesCompra();
            return ResponseEntity.ok(ordenesCompra);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // PUT /ordenes_compra/modificar
    @PutMapping("/modificar")
    public ResponseEntity<?> modificarEstadoOrdenCompra(@RequestBody Map<String, Object> data) {
        Long ordenId = Long.valueOf(data.get("orden_id").toString());

        if (ordenId == null) {
            return ResponseEntity.badRequest().body("Falta el parámetro requerido: orden_id");
        }

        boolean actualizado = modificarEstadoService.modificarEstadoOrdenCompra(ordenId);

        if (!actualizado) {
            return ResponseEntity.status(404).body("Orden no encontrada o no pudo ser actualizada");
        }

        return ResponseEntity.ok("Estado de la orden de compra actualizado con éxito");
    }
}
