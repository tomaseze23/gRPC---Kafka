package proveedor.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import proveedor.dto.*;
import proveedor.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private OrdenCompraService ordenDeCompraService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "orden-de-compra", groupId = "grupo-consumidores")
    public void listen(String message) {
        try {
            if (message.contains("tienda_id")) {
                OrdenCompraDTO data = objectMapper.readValue(message, OrdenCompraDTO.class);
                processOrdenCompraMessage(data);
            } else if (message.contains("orden_id") && message.contains("despacho_id")) {
                OrdenRecibidaDTO data = objectMapper.readValue(message, OrdenRecibidaDTO.class);
                processOrdenRecibidaMessage(data);
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error al deserializar el mensaje: " + e.getMessage());
        }
    }

    private void processOrdenCompraMessage(OrdenCompraDTO message) {
        try {
            Long ordenId = ordenDeCompraService.crearOrdenCompra(message);
            System.out.println("Orden de compra creada con ID: " + ordenId);
        } catch (Exception e) {
            System.err.println("Error al procesar la orden de compra: " + e.getMessage());
        }
    }

    private void processOrdenRecibidaMessage(OrdenRecibidaDTO message) {
        try {
            ordenDeCompraService.marcarOrdenRecibida(message.getOrden_id(), message.getDespacho_id());
            System.out.println("Orden de compra marcada como recibida: " + message.getOrden_id());
        } catch (Exception e) {
            System.err.println("Error al procesar la orden recibida: " + e.getMessage());
        }
    }
}
