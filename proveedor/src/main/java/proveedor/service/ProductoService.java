// ProductoService.java
package proveedor.service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import proveedor.dto.ProductoDTO;
import proveedor.models.ItemOrdenCompra;
import proveedor.models.OrdenCompra;
import proveedor.models.OrdenDespacho;
import proveedor.models.Producto;
import proveedor.repository.ItemOrdenCompraRepository;
import proveedor.repository.OrdenCompraRepository;
import proveedor.repository.OrdenDespachoRepository;
import proveedor.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private ItemOrdenCompraRepository itemOrdenCompraRepository;

    @Autowired
    private OrdenDespachoRepository ordenDespachoRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    @Transactional
    public void modificarStock(Long productoId, Integer nuevaCantidad) throws Exception {
        Producto producto = productoRepository.findById(productoId).orElse(null);
        if (producto == null) {
            throw new Exception("Producto no encontrado");
        }

        producto.setCantidadStockProveedor(nuevaCantidad);
        productoRepository.save(producto);

        // Reprocesar órdenes pausadas
        List<OrdenCompra> ordenesPausadas = ordenCompraRepository.findOrdenesPausadasPorProducto(productoId);

        for (OrdenCompra orden : ordenesPausadas) {
            List<ItemOrdenCompra> items = itemOrdenCompraRepository.findByOrdenCompraId(orden.getId());

            boolean puedeActualizar = true;

            for (ItemOrdenCompra item : items) {
                if (item.getProducto().getId().equals(productoId)) {
                    if (item.getCantidad() > nuevaCantidad) {
                        puedeActualizar = false;
                        break;
                    }
                }
            }

            if (puedeActualizar) {
                orden.setEstado("ACEPTADA");
                ordenCompraRepository.save(orden);

                // Generar orden de despacho
                OrdenDespacho ordenDespacho = new OrdenDespacho();
                ordenDespacho.setOrdenCompra(orden);
                ordenDespacho.setFechaEstimacionEnvio(OffsetDateTime.now().plusDays(7));
                ordenDespacho.setEstado("PENDIENTE");
                ordenDespachoRepository.save(ordenDespacho);

                // Enviar mensaje a Kafka
                Map<String, Object> mensajeDespacho = new HashMap<>();
                mensajeDespacho.put("orden_despacho_id", ordenDespacho.getId());
                mensajeDespacho.put("orden_compra_id", orden.getId());
                mensajeDespacho.put("fecha_estimacion_envio", ordenDespacho.getFechaEstimacionEnvio().toString());

                kafkaProducerService.sendMessage("despacho", mensajeDespacho);

                // Restar stock
                //TODO: Revisar esto
                //nuevaCantidad -= item.getCantidad();
                producto.setCantidadStockProveedor(nuevaCantidad);
                productoRepository.save(producto);
            }
        }
    }

    public Producto altaProducto(ProductoDTO productoDTO) throws Exception {
        // Validar los datos
        Producto producto = new Producto();
        producto.setCodigo(productoDTO.getCodigo());
        producto.setNombre(productoDTO.getNombre());
        producto.setTalle(String.join(",", productoDTO.getTalles()));
        producto.setColor(String.join(",", productoDTO.getColores()));
        producto.setFoto(String.join(",", productoDTO.getUrls()));
        producto.setCantidadStockProveedor(productoDTO.getCantidadStockProveedor());
        productoRepository.save(producto);

        // Enviar mensaje a Kafka
        kafkaProducerService.sendMessage("novedades", productoDTO);

        return producto;
    }
}
