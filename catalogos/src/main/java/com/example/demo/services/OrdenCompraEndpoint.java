package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.models.OrdenCompra;
import com.example.demo.repository.OrdenCompraRepository;
import com.example.ordenes_compra.GetOrdenCompraRequest;
import com.example.ordenes_compra.GetOrdenCompraResponse;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Endpoint
public class OrdenCompraEndpoint {

    private static final String NAMESPACE_URI = "http://www.example.com/ordenes_compra";

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrdenCompraRequest")
    @ResponsePayload
    public GetOrdenCompraResponse getOrdenCompra(@RequestPayload GetOrdenCompraRequest request) {
        GetOrdenCompraResponse response = new GetOrdenCompraResponse();

        Optional<OrdenCompra> ordenCompraOptional = ordenCompraRepository.findById(request.getId());

        if (ordenCompraOptional.isPresent()) {
            OrdenCompra ordenCompra = ordenCompraOptional.get();
            com.example.ordenes_compra.OrdenCompra ordenCompraResponse = new com.example.ordenes_compra.OrdenCompra();

            // Asignar valores del modelo a la respuesta
            ordenCompraResponse.setId(ordenCompra.getId());
            ordenCompraResponse.setTiendaId(ordenCompra.getTienda().getCodigo());

            if (ordenCompra.getFechaSolicitud() != null) {
                ordenCompraResponse.setFechaSolicitud(convertToXMLGregorianCalendar(ordenCompra.getFechaSolicitud()));
            }
            if (ordenCompra.getFechaEnvio() != null) {
                ordenCompraResponse.setFechaEnvio(convertToXMLGregorianCalendar(ordenCompra.getFechaEnvio()));
            }
            if (ordenCompra.getFechaRecepcion() != null) {
                ordenCompraResponse.setFechaRecepcion(convertToXMLGregorianCalendar(ordenCompra.getFechaRecepcion()));
            }

            ordenCompraResponse.setEstado(ordenCompra.getEstado());
            ordenCompraResponse.setObservaciones(ordenCompra.getObservaciones());

            response.setOrdenCompra(ordenCompraResponse);
        }

        return response;
    }

    private XMLGregorianCalendar convertToXMLGregorianCalendar(OffsetDateTime dateTime) {
        try {
            if (dateTime == null) {
                return null;
            }
            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(dateTime.atZoneSameInstant(ZoneId.systemDefault()).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
