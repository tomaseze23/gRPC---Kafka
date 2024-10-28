package com.example.demo.services;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.catalogo.CreateCatalogoRequest;
import com.example.catalogo.CreateCatalogoResponse;
import com.example.catalogo.DeleteCatalogoRequest;
import com.example.catalogo.DeleteCatalogoResponse;
import com.example.catalogo.GetAllCatalogosRequest;
import com.example.catalogo.GetAllCatalogosResponse;
import com.example.catalogo.GetCatalogoRequest;
import com.example.catalogo.GetCatalogoResponse;
import com.example.catalogo.UpdateCatalogoRequest;
import com.example.catalogo.UpdateCatalogoResponse;
import com.example.demo.models.Catalogo;
import com.example.demo.models.Tienda;
import com.example.demo.repository.CatalogoRepository;
import com.example.demo.repository.TiendaRepository;

@Endpoint
public class CatalogoEndpoint {

    private static final String NAMESPACE_URI = "http://www.example.com/catalogo";

    @Autowired
    private CatalogoRepository catalogoRepository;
    @Autowired
    private TiendaRepository tiendaRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCatalogoRequest")
    @ResponsePayload
    public GetCatalogoResponse getCatalogo(@RequestPayload GetCatalogoRequest request) {
        GetCatalogoResponse response = new GetCatalogoResponse();
        Optional<Catalogo> catalogoOptional = catalogoRepository.findById(request.getId());

        if (catalogoOptional.isPresent()) {
            Catalogo catalogo = catalogoOptional.get();
            response.setCatalogo(mapCatalogoToResponse(catalogo));
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCatalogoRequest")
    @ResponsePayload
    public CreateCatalogoResponse createCatalogo(@RequestPayload CreateCatalogoRequest request) {
        CreateCatalogoResponse response = new CreateCatalogoResponse();

        // Buscar la tienda por su ID
        Optional<Tienda> tiendaOptional = tiendaRepository.findById(request.getCatalogo().getTiendaId());
        if (tiendaOptional.isPresent()) {
            Catalogo catalogo = new Catalogo();
            catalogo.setTienda(tiendaOptional.get());
            catalogo.setNombreCatalogo(request.getCatalogo().getNombreCatalogo());
            catalogo.setFechaCreacion(OffsetDateTime.now());

            catalogo = catalogoRepository.save(catalogo);
            response.setCatalogoId(catalogo.getId());
        } else {
            throw new RuntimeException("Tienda con ID " + request.getCatalogo().getTiendaId() + " no encontrada");
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCatalogoRequest")
    @ResponsePayload
    public UpdateCatalogoResponse updateCatalogo(@RequestPayload UpdateCatalogoRequest request) {
        UpdateCatalogoResponse response = new UpdateCatalogoResponse();
        Optional<Catalogo> catalogoOptional = catalogoRepository.findById(request.getCatalogo().getId());

        if (catalogoOptional.isPresent()) {
            Catalogo catalogo = catalogoOptional.get();
            catalogo.setNombreCatalogo(request.getCatalogo().getNombreCatalogo());
            catalogo.setFechaCreacion(request.getCatalogo().getFechaCreacion() != null
                    ? OffsetDateTime.parse(request.getCatalogo().getFechaCreacion().toString())
                    : OffsetDateTime.now());
            catalogoRepository.save(catalogo);
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCatalogoRequest")
    @ResponsePayload
    public DeleteCatalogoResponse deleteCatalogo(@RequestPayload DeleteCatalogoRequest request) {
        DeleteCatalogoResponse response = new DeleteCatalogoResponse();
        Optional<Catalogo> catalogoOptional = catalogoRepository.findById(request.getId());

        if (catalogoOptional.isPresent()) {
            catalogoRepository.deleteById(request.getId());
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
        }
        return response;
    }

    private com.example.catalogo.Catalogo mapCatalogoToResponse(Catalogo catalogo) {
        com.example.catalogo.Catalogo responseCatalogo = new com.example.catalogo.Catalogo();
        responseCatalogo.setId(catalogo.getId());
        responseCatalogo.setTiendaId(catalogo.getTienda().getCodigo());
        responseCatalogo.setNombreCatalogo(catalogo.getNombreCatalogo());
        if (catalogo.getFechaCreacion() != null) {
            responseCatalogo.setFechaCreacion(convertToXMLGregorianCalendar(catalogo.getFechaCreacion()));
        }
        return responseCatalogo;
    }

    private XMLGregorianCalendar convertToXMLGregorianCalendar(OffsetDateTime dateTime) {
        try {
            if (dateTime == null) {
                return null;
            }
            ZonedDateTime zonedDateTime = dateTime.atZoneSameInstant(ZoneId.systemDefault());

            GregorianCalendar gregorianCalendar = GregorianCalendar.from(zonedDateTime);

            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCatalogosRequest")
    @ResponsePayload
    public GetAllCatalogosResponse getAllCatalogos(@RequestPayload GetAllCatalogosRequest request) {
        GetAllCatalogosResponse response = new GetAllCatalogosResponse();

        List<Catalogo> catalogos = catalogoRepository.findAll();
        for (Catalogo catalogo : catalogos) {
            response.getCatalogo().add(mapCatalogoToResponse(catalogo));
        }

        return response;
    }

}
