package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.models.Tienda;
import com.example.demo.repository.TiendaRepository;
import com.example.tiendas.GetTiendaRequest;  // Asegúrate de que esta clase exista
import com.example.tiendas.GetTiendaResponse; // Asegúrate de que esta clase exista

@Endpoint
public class TiendaEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/tiendas";

    @Autowired
    private TiendaRepository tiendaRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTiendaRequest")
    @ResponsePayload
    public GetTiendaResponse getTienda(@RequestPayload GetTiendaRequest request) {
        GetTiendaResponse response = new GetTiendaResponse();
        Tienda tienda = tiendaRepository.findById(request.getCodigo()).orElse(null);

        com.example.tiendas.Tienda tiendaResponse = new com.example.tiendas.Tienda();
        if (tienda != null) {
            tiendaResponse.setCodigo(tienda.getCodigo());
            tiendaResponse.setDireccion(tienda.getDireccion());
            tiendaResponse.setCiudad(tienda.getCiudad());
            tiendaResponse.setProvincia(tienda.getProvincia());
            tiendaResponse.setHabilitada(tienda.isHabilitada());
            response.setTienda(tiendaResponse);
        }
        return response;
    }
}
