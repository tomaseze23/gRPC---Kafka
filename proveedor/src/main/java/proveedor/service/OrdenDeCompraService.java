// OrdenDeCompraService.java
package proveedor.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import proveedor.dto.ItemOrdenCompraDTO;
import proveedor.dto.OrdenCompraDTO;
import proveedor.service.KafkaProducerService;
import proveedor.models.*;
import proveedor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdenDeCompraService {

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

    @Autowired
    private TiendaRepository tiendaRepository;

    @Transactional
    public Long crearOrdenCompra(OrdenCompraDTO dto) {
        // Validar los ítems
        List<String> errores = new ArrayList<>();
        List<Map<String, Object>> faltantesStock = new ArrayList<>();

        Map<String, Integer> productosAgrupados = new HashMap<>();

        for (ItemOrdenCompraDTO item : dto.getItems()) {
            String productoId = item.getProducto_id();

            if (item.getCantidad() < 1) {
                errores.add("Artículo " + productoId + ": cantidad mal informada.");
                continue;
            }

            productosAgrupados.merge(productoId, item.getCantidad(), Integer::sum);
        }

        // Validar stock
        for (Map.Entry<String, Integer> entry : productosAgrupados.entrySet()) {
            String productoId = entry.getKey();
            Integer cantidadSolicitada = entry.getValue();

            Optional<Producto> productoOpt = productoRepository.findByCodigo(productoId);
            if (!productoOpt.isPresent()) {
                errores.add("Artículo " + productoId + ": no existe en el inventario.");
            } else {
                Producto producto = productoOpt.get();
                if (cantidadSolicitada > producto.getCantidadStockProveedor()) {
                    Map<String, Object> faltante = new HashMap<>();
                    faltante.put("producto_id", productoId);
                    faltante.put("cantidad_solicitada", cantidadSolicitada);
                    faltante.put("cantidad_disponible", producto.getCantidadStockProveedor());
                    faltantesStock.add(faltante);
                }
            }
        }

        // Generar estado y observaciones
        String estado;
        String observaciones;
        if (!errores.isEmpty()) {
            estado = "RECHAZADA";
            observaciones = String.join("; ", errores);
        } else if (!faltantesStock.isEmpty()) {
            estado = "PAUSADA";
            observaciones = "Artículos faltantes: " + faltantesStock.stream()
                    .map(f -> f.get("producto_id") + ": solicitado " + f.get("cantidad_solicitada") + ", disponible "
                            + f.get("cantidad_disponible"))
                    .collect(Collectors.joining("; "));
        } else {
            estado = "ACEPTADA";
            observaciones = "";
        }

        // Insertar orden de compra
        OrdenCompra ordenCompra = new OrdenCompra();
        // Suponiendo que tienes un TiendaRepository
        Optional<Tienda> tiendaOpt = tiendaRepository.findByCodigo(dto.getTienda_id());
        if (tiendaOpt.isPresent()) {
            ordenCompra.setTienda(tiendaOpt.get());
        } else {
            errores.add("Tienda " + dto.getTienda_id() + ": no existe.");
            // Manejar el error según corresponda
        }
        ordenCompra.setEstado(estado);
        ordenCompra.setObservaciones(observaciones);
        ordenCompra.setFechaSolicitud(OffsetDateTime.now());
        ordenCompra = ordenCompraRepository.save(ordenCompra);

        for (ItemOrdenCompraDTO itemDTO : dto.getItems()) {
            if (itemDTO.getCantidad() > 0) {
                ItemOrdenCompra itemOrdenCompra = new ItemOrdenCompra();
                itemOrdenCompra.setOrdenCompra(ordenCompra);

                // Recuperar el Producto existente
                Optional<Producto> productoOpt = productoRepository.findByCodigo(itemDTO.getProducto_id());
                if (productoOpt.isPresent()) {
                    itemOrdenCompra.setProducto(productoOpt.get());
                } else {
                    errores.add("Artículo " + itemDTO.getProducto_id() + ": no existe en el inventario.");
                    continue; // O manejar el error según corresponda
                }

                itemOrdenCompra.setColor(itemDTO.getColor());
                itemOrdenCompra.setTalle(itemDTO.getTalle());
                itemOrdenCompra.setCantidad(itemDTO.getCantidad());
                itemOrdenCompraRepository.save(itemOrdenCompra);
            }
        }

        // Actualizar stock si no hay errores ni faltantes
        if (errores.isEmpty() && faltantesStock.isEmpty()) {
            actualizarStock(dto.getItems());
            // Generar orden de despacho
            Long ordenDespachoId = generarOrdenDespacho(ordenCompra.getId());
            // Enviar mensaje de despacho
            enviarMensajeDespacho(dto.getTienda_id(), ordenDespachoId, ordenCompra.getId());
        }

        // Enviar mensaje a Kafka
        enviarMensajeKafka(dto.getTienda_id(), ordenCompra.getId(), estado, observaciones, dto.getItems());

        return ordenCompra.getId();
    }

    private void actualizarStock(List<ItemOrdenCompraDTO> items) {
        for (ItemOrdenCompraDTO item : items) {
            Optional<Producto> productoOpt = productoRepository.findByCodigo(item.getProducto_id());
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                producto.setCantidadStockProveedor(producto.getCantidadStockProveedor() - item.getCantidad());
                productoRepository.save(producto);
            }
        }
    }

    private Long generarOrdenDespacho(Long ordenCompraId) {
        OrdenDespacho ordenDespacho = new OrdenDespacho();
        OrdenCompra ordenCompra = ordenCompraRepository.findById(ordenCompraId)
                .orElseThrow(() -> new RuntimeException("OrdenCompra no encontrada"));
        ordenDespacho.setOrdenCompra(ordenCompra);
        ordenDespacho.setFechaEstimacionEnvio(OffsetDateTime.now().plusDays(7));
        ordenDespacho.setEstado("PENDIENTE");
        ordenDespacho = ordenDespachoRepository.save(ordenDespacho);
        return ordenDespacho.getId();
    }

    private void enviarMensajeDespacho(String tiendaId, Long ordenDespachoId, Long ordenCompraId) {
        Map<String, Object> mensajeDespacho = new HashMap<>();
        mensajeDespacho.put("orden_despacho_id", ordenDespachoId);
        mensajeDespacho.put("orden_compra_id", ordenCompraId);
        mensajeDespacho.put("fecha_estimacion_envio", OffsetDateTime.now().plusDays(7).toString());

        kafkaProducerService.sendMessage("despacho", mensajeDespacho);
    }

    private void enviarMensajeKafka(String tiendaId, Long ordenCompraId, String estado, String observaciones,
            List<ItemOrdenCompraDTO> items) {
        Map<String, Object> mensajeKafka = new HashMap<>();
        mensajeKafka.put("orden_id", ordenCompraId);
        mensajeKafka.put("tienda_id", tiendaId);
        mensajeKafka.put("estado", estado.equals("PAUSADA") ? "ACEPTADA" : estado);
        mensajeKafka.put("observaciones", observaciones);
        mensajeKafka.put("items", items);

        // Convertir el mensaje a JSON
        try {
            ObjectMapper mapper = new ObjectMapper();
            String mensajeJson = mapper.writeValueAsString(mensajeKafka);
            kafkaProducerService.sendMessage("solicitudes", mensajeJson);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar error de serialización
        }
    }

    public List<OrdenCompra> obtenerOrdenesCompra() {
        return ordenCompraRepository.findAll();
    }

    public List<ItemOrdenCompra> obtenerItemsPorOrden(Long ordenCompraId) {
        return itemOrdenCompraRepository.findByOrdenCompraId(ordenCompraId);
    }

    public Optional<Producto> obtenerArticuloPorId(Long articuloId) {
        return productoRepository.findById(articuloId);
    }

    @Transactional
    public boolean marcarOrdenRecibida(Long ordenId, Long despachoId) {
        Optional<OrdenCompra> ordenOpt = ordenCompraRepository.findById(ordenId);
        if (!ordenOpt.isPresent()) {
            System.out.println("Orden " + ordenId + " no existe en la base de datos.");
            return false;
        }

        OrdenCompra orden = ordenOpt.get();
        String estado = orden.getEstado();

        if (!estado.equals("ACEPTADA")) {
            System.out.println("Orden " + ordenId
                    + " no puede ser marcada como recibida porque no está en estado ACEPTADA. Está en " + estado);
            return false;
        }

        orden.setFechaRecepcion(OffsetDateTime.now());
        ordenCompraRepository.save(orden);

        System.out.println("Orden " + ordenId + " marcada como recibida con despacho " + despachoId + ".");
        return true;
    }

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
            nuevoEstado = "RECHAZADA";
            String mensaje = String.join(", ", errores);
            mensajeKafka.put("orden_id", ordenId);
            mensajeKafka.put("nuevo_estado", nuevoEstado);
            mensajeKafka.put("errores", mensaje);
            kafkaProducerService.sendMessage("solicitudes", mensajeKafka);
        } else if (!faltantesStock.isEmpty()) {
            nuevoEstado = "PAUSADA";
            String mensaje = "Artículos sin stock: " + String.join(", ", faltantesStock);
            mensajeKafka.put("orden_id", ordenId);
            mensajeKafka.put("nuevo_estado", nuevoEstado);
            mensajeKafka.put("faltantes", mensaje);
            kafkaProducerService.sendMessage("solicitudes", mensajeKafka);
        } else {
            nuevoEstado = "ACEPTADA";
            mensajeKafka.put("orden_id", ordenId);
            mensajeKafka.put("nuevo_estado", nuevoEstado);
            kafkaProducerService.sendMessage("solicitudes", mensajeKafka);

            // Generar orden de despacho
            OrdenDespacho ordenDespacho = new OrdenDespacho();
            ordenDespacho.setOrdenCompra(orden);
            ordenDespacho.setFechaEstimacionEnvio(OffsetDateTime.now().plusDays(7));
            ordenDespacho.setEstado("PENDIENTE");
            ordenDespachoRepository.save(ordenDespacho);

            // Enviar mensaje de despacho
            Map<String, Object> mensajeDespacho = new HashMap<>();
            mensajeDespacho.put("orden_despacho_id", ordenDespacho.getId());
            mensajeDespacho.put("orden_compra_id", ordenId);
            mensajeDespacho.put("fecha_estimacion_envio", ordenDespacho.getFechaEstimacionEnvio().toString());

            kafkaProducerService.sendMessage("despacho", mensajeDespacho);

            // Restar stock
            for (ItemOrdenCompra item : items) {
                Producto producto = item.getProducto();
                producto.setCantidadStockProveedor(producto.getCantidadStockProveedor() - item.getCantidad());
                productoRepository.save(producto);
            }
        }

        // Actualizar estado de la orden
        orden.setEstado(nuevoEstado);
        ordenCompraRepository.save(orden);

        return true;
    }

}
