package proveedor.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import proveedor.dto.ItemOrdenCompraDTO;
import proveedor.dto.OrdenCompraDTO;
import proveedor.enums.EstadoOrdenCompra;
import proveedor.models.ItemOrdenCompra;
import proveedor.models.OrdenCompra;
import proveedor.models.OrdenDespacho;
import proveedor.models.Producto;
import proveedor.models.Tienda;
import proveedor.repository.ItemOrdenCompraRepository;
import proveedor.repository.OrdenCompraRepository;
import proveedor.repository.OrdenDespachoRepository;
import proveedor.repository.ProductoRepository;
import proveedor.repository.TiendaRepository;

@Service
public class OrdenCompraService {

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

        String estado;
        String observaciones;
        if (!errores.isEmpty()) {
            estado = EstadoOrdenCompra.RECHAZADA.name();
            observaciones = String.join("; ", errores);
        } else if (!faltantesStock.isEmpty()) {
            estado = EstadoOrdenCompra.SOLICITADA.name();
            observaciones = "Artículos faltantes: " + faltantesStock.stream()
                    .map(f -> f.get("producto_id") + ": solicitado " + f.get("cantidad_solicitada") + ", disponible "
                            + f.get("cantidad_disponible"))
                    .collect(Collectors.joining("; "));
        } else {
            estado = EstadoOrdenCompra.ACEPTADA.name();
            observaciones = "";
        }

        OrdenCompra ordenCompra = new OrdenCompra();
        Optional<Tienda> tiendaOpt = tiendaRepository.findByCodigo(dto.getTienda_id());
        if (tiendaOpt.isPresent()) {
            ordenCompra.setTienda(tiendaOpt.get());
        } else {
            errores.add("Tienda " + dto.getTienda_id() + ": no existe.");
        }
        ordenCompra.setEstado(estado);
        ordenCompra.setObservaciones(observaciones);
        ordenCompra.setFechaSolicitud(OffsetDateTime.now());
        ordenCompra = ordenCompraRepository.save(ordenCompra);

        for (ItemOrdenCompraDTO itemDTO : dto.getItems()) {
            if (itemDTO.getCantidad() > 0) {
                ItemOrdenCompra itemOrdenCompra = new ItemOrdenCompra();
                itemOrdenCompra.setOrdenCompra(ordenCompra);

                Optional<Producto> productoOpt = productoRepository.findByCodigo(itemDTO.getProducto_id());
                if (productoOpt.isPresent()) {
                    itemOrdenCompra.setProducto(productoOpt.get());
                } else {
                    errores.add("Artículo " + itemDTO.getProducto_id() + ": no existe en el inventario.");
                    continue; 
                }

                itemOrdenCompra.setColor(itemDTO.getColor());
                itemOrdenCompra.setTalle(itemDTO.getTalle());
                itemOrdenCompra.setCantidad(itemDTO.getCantidad());
                itemOrdenCompraRepository.save(itemOrdenCompra);
            }
        }

        if (errores.isEmpty() && faltantesStock.isEmpty()) {
            actualizarStock(dto.getItems());
            Long ordenDespachoId = generarOrdenDespacho(ordenCompra.getId());
            enviarMensajeDespacho(dto.getTienda_id(), ordenDespachoId, ordenCompra.getId());
        }
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

        try {
            ObjectMapper mapper = new ObjectMapper();
            String mensajeJson = mapper.writeValueAsString(mensajeKafka);
            kafkaProducerService.sendMessage("solicitudes", mensajeJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OrdenCompra> obtenerOrdenesCompra() {
        return ordenCompraRepository.findAll();
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

        if (!estado.equals(EstadoOrdenCompra.ACEPTADA.name())) {
            System.out.println("Orden " + ordenId
                    + " no puede ser marcada como recibida porque no está en estado ACEPTADA. Está en " + estado);
            return false;
        }

        orden.setFechaRecepcion(OffsetDateTime.now());
        ordenCompraRepository.save(orden);

        System.out.println("Orden " + ordenId + " marcada como recibida con despacho " + despachoId + ".");
        return true;
    }

}
