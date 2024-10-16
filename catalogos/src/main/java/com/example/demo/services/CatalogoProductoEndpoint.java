package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.models.CatalogoProducto;
import com.example.demo.repository.CatalogoProductoRepository;
import com.example.catalogoproducto.GetCatalogoProductoRequest;
import com.example.catalogoproducto.GetCatalogoProductoResponse;

@Endpoint
public class CatalogoProductoEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/catalogo_producto";

    @Autowired
    private CatalogoProductoRepository catalogoProductoRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCatalogoProductoRequest")
    @ResponsePayload
    public GetCatalogoProductoResponse getCatalogoProducto(@RequestPayload GetCatalogoProductoRequest request) {
        GetCatalogoProductoResponse response = new GetCatalogoProductoResponse();
        CatalogoProducto catalogoProducto = catalogoProductoRepository.findById(request.getId()).orElse(null);

        if (catalogoProducto != null) {
            com.example.catalogoproducto.CatalogoProducto catalogoProductoResponse = new com.example.catalogoproducto.CatalogoProducto();
            catalogoProductoResponse.setId(catalogoProducto.getId());
            catalogoProductoResponse.setCatalogoId(catalogoProducto.getCatalogo().getId()); // Asegúrate de que el tipo sea correcto
            catalogoProductoResponse.setNombreProducto(catalogoProducto.getProducto().getNombre()); // Asegúrate de que el tipo sea correcto
            response.setCatalogoProducto(catalogoProductoResponse);
        }

        return response;
    }
}
