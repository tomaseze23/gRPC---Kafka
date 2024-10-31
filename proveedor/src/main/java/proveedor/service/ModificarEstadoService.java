package proveedor.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import proveedor.enums.EstadoDespacho;
import proveedor.enums.EstadoOrdenCompra;
import proveedor.models.ItemOrdenCompra;
import proveedor.models.OrdenCompra;
import proveedor.models.OrdenDespacho;
import proveedor.models.Producto;
import proveedor.repository.ItemOrdenCompraRepository;
import proveedor.repository.OrdenCompraRepository;
import proveedor.repository.OrdenDespachoRepository;
import proveedor.repository.ProductoRepository;

@Service
public class ModificarEstadoService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private ItemOrdenCompraRepository itemOrdenCompraRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private OrdenDespachoRepository ordenDespachoRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;


    @Transactional
    public boolean modificarEstadoOrdenCompra(Long ordenId) {
        OrdenCompra orden = ordenCompraRepository.findById(ordenId).orElse(null);
        if (orden == null) {
            return false;
        }

        List<String> errores = new ArrayList<>();
        List<String> faltantesStock = new ArrayList<>();

        List<ItemOrdenCompra> items = itemOrdenCompraRepository.findByOrdenCompraId(ordenId);

        for (ItemOrdenCompra item : items) {
            Producto producto = productoRepository.findById(item.getProducto().getId()).orElse(null);
            if (producto == null || item.getCantidad() < 1) {
                errores.add("Artículo " + item.getProducto().getId() + ": "
                        + (producto == null ? "no existe" : "cantidad mal informada"));
            } else if (item.getCantidad() > producto.getCantidadStockProveedor()) {
                faltantesStock.add(item.getProducto().getId().toString());
            }
        }

        String nuevoEstado;
        Map<String, Object> mensajeKafka = new HashMap<>();

        if (!errores.isEmpty()) {
            nuevoEstado = EstadoOrdenCompra.RECHAZADA.name();
            String mensaje = String.join(", ", errores);
            mensajeKafka.put("orden_id", ordenId);
            mensajeKafka.put("nuevo_estado", nuevoEstado);
            mensajeKafka.put("errores", mensaje);
            kafkaProducerService.sendMessage("solicitudes", mensajeKafka);
        } else if (!faltantesStock.isEmpty()) {
            nuevoEstado = EstadoOrdenCompra.SOLICITADA.name();
            String mensaje = "Artículos sin stock: " + String.join(", ", faltantesStock);
            mensajeKafka.put("orden_id", ordenId);
            mensajeKafka.put("nuevo_estado", nuevoEstado);
            mensajeKafka.put("faltantes", mensaje);
            kafkaProducerService.sendMessage("solicitudes", mensajeKafka);
        } else {
            nuevoEstado = EstadoOrdenCompra.ACEPTADA.name();
            mensajeKafka.put("orden_id", ordenId);
            mensajeKafka.put("nuevo_estado", nuevoEstado);
            kafkaProducerService.sendMessage("solicitudes", mensajeKafka);

            OrdenDespacho ordenDespacho = new OrdenDespacho();
            ordenDespacho.setOrdenCompra(orden);
            ordenDespacho.setFechaEstimacionEnvio(OffsetDateTime.now().plusDays(7));
            ordenDespacho.setEstado(EstadoDespacho.ENVIADO.name());
            ordenDespachoRepository.save(ordenDespacho);

            Map<String, Object> mensajeDespacho = new HashMap<>();
            mensajeDespacho.put("orden_despacho_id", ordenDespacho.getId());
            mensajeDespacho.put("orden_compra_id", ordenId);
            mensajeDespacho.put("fecha_estimacion_envio", ordenDespacho.getFechaEstimacionEnvio().toString());

            kafkaProducerService.sendMessage("despacho", mensajeDespacho);

            for (ItemOrdenCompra item : items) {
                Producto producto = item.getProducto();
                producto.setCantidadStockProveedor(producto.getCantidadStockProveedor() - item.getCantidad());
                productoRepository.save(producto);
            }
        }

        orden.setEstado(nuevoEstado);
        ordenCompraRepository.save(orden);

        return true;
    }
}
