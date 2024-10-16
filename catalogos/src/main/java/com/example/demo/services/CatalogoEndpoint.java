package com.example.demo.services;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.models.Catalogo;
import com.example.demo.repository.CatalogoRepository;
import com.example.catalogo.GetCatalogoRequest;
import com.example.catalogo.GetCatalogoResponse;

@Endpoint
public class CatalogoEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/catalogo";

    @Autowired
    private CatalogoRepository catalogoRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCatalogoRequest")
    @ResponsePayload
    public GetCatalogoResponse getCatalogo(@RequestPayload GetCatalogoRequest request)
            throws DatatypeConfigurationException {
        GetCatalogoResponse response = new GetCatalogoResponse();
        Catalogo catalogo = catalogoRepository.findById(request.getId()).orElse(null);

        com.example.catalogo.Catalogo catalogoResponse = new com.example.catalogo.Catalogo();
        if (catalogo != null) {
            catalogoResponse.setId(catalogo.getId());
            catalogoResponse.setTiendaId(catalogo.getTienda().getCodigo()); 
            catalogoResponse.setNombreCatalogo(catalogo.getNombreCatalogo());
            catalogoResponse.setFechaCreacion(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(catalogo.getFechaCreacion().toZonedDateTime().toString()));
            response.setCatalogo(catalogoResponse);
        }
        return response;
    }
}
