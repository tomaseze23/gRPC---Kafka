package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.catalogoproducto.CreateCatalogoProductoRequest;
import com.example.catalogoproducto.CreateCatalogoProductoResponse;
import com.example.catalogoproducto.DeleteCatalogoProductoRequest;
import com.example.catalogoproducto.DeleteCatalogoProductoResponse;
import com.example.catalogoproducto.GetAllCatalogoProductoByCatalogoRequest;
import com.example.catalogoproducto.GetAllCatalogoProductoByCatalogoResponse;
import com.example.catalogoproducto.GetAllCatalogoProductoByTiendaRequest;
import com.example.catalogoproducto.GetAllCatalogoProductoByTiendaResponse;
import com.example.catalogoproducto.GetCatalogoProductoRequest;
import com.example.catalogoproducto.GetCatalogoProductoResponse;
import com.example.catalogoproducto.UpdateCatalogoProductoRequest;
import com.example.catalogoproducto.UpdateCatalogoProductoResponse;
import com.example.demo.models.Catalogo;
import com.example.demo.models.CatalogoProducto;
import com.example.demo.models.Producto;
import com.example.demo.repository.CatalogoProductoRepository;
import com.example.demo.repository.CatalogoRepository;
import com.example.demo.repository.ProductoRepository;

@Endpoint
public class CatalogoProductoEndpoint {

    private static final String NAMESPACE_URI = "http://www.example.com/catalogoProducto";

    @Autowired
    private CatalogoProductoRepository catalogoProductoRepository;

    @Autowired
    private CatalogoRepository catalogoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCatalogoProductoRequest")
    @ResponsePayload
    public GetCatalogoProductoResponse getCatalogoProducto(@RequestPayload GetCatalogoProductoRequest request) {
        GetCatalogoProductoResponse response = new GetCatalogoProductoResponse();
        Optional<CatalogoProducto> catalogoProductoOptional = catalogoProductoRepository.findById(request.getId());

        if (catalogoProductoOptional.isPresent()) {
            response.setCatalogoProducto(mapCatalogoProductoToResponse(catalogoProductoOptional.get()));
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCatalogoProductoRequest")
    @ResponsePayload
    public CreateCatalogoProductoResponse createCatalogoProducto(
            @RequestPayload CreateCatalogoProductoRequest request) {
        CreateCatalogoProductoResponse response = new CreateCatalogoProductoResponse();

        Optional<Catalogo> catalogoOptional = catalogoRepository
                .findById(request.getCatalogoProducto().getCatalogoId());
        Optional<Producto> productoOptional = productoRepository.findByCodigo(request.getCatalogoProducto().getId());

        if (catalogoOptional.isPresent() && productoOptional.isPresent()) {
            CatalogoProducto catalogoProducto = new CatalogoProducto();
            catalogoProducto.setCatalogo(catalogoOptional.get());
            catalogoProducto.setProducto(productoOptional.get());

            catalogoProducto = catalogoProductoRepository.save(catalogoProducto);
            response.setCatalogoProductoId(catalogoProducto.getId());
        } else {
            throw new RuntimeException("Catálogo o Producto no encontrado");
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCatalogoProductoRequest")
    @ResponsePayload
    public UpdateCatalogoProductoResponse updateCatalogoProducto(
            @RequestPayload UpdateCatalogoProductoRequest request) {
        UpdateCatalogoProductoResponse response = new UpdateCatalogoProductoResponse();
        Optional<CatalogoProducto> catalogoProductoOptional = catalogoProductoRepository
                .findByProductoCodigo(request.getCatalogoProducto().getId());

        if (catalogoProductoOptional.isPresent()) {
            CatalogoProducto catalogoProducto = catalogoProductoOptional.get();

            Optional<Catalogo> catalogoOptional = catalogoRepository
                    .findById(request.getCatalogoProducto().getCatalogoId());
            Optional<Producto> productoOptional = productoRepository
                    .findByCodigo(request.getCatalogoProducto().getId());

            if (catalogoOptional.isPresent() && productoOptional.isPresent()) {
                catalogoProducto.setCatalogo(catalogoOptional.get());
                catalogoProducto.setProducto(productoOptional.get());

                catalogoProductoRepository.save(catalogoProducto);
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
            }
        } else {
            response.setSuccess(false);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCatalogoProductoRequest")
    @ResponsePayload
    public DeleteCatalogoProductoResponse deleteCatalogoProducto(
            @RequestPayload DeleteCatalogoProductoRequest request) {
        DeleteCatalogoProductoResponse response = new DeleteCatalogoProductoResponse();
        Optional<CatalogoProducto> catalogoProductoOptional = catalogoProductoRepository.findById(request.getId());

        if (catalogoProductoOptional.isPresent()) {
            catalogoProductoRepository.deleteById(request.getId());
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
        }

        return response;
    }

    private com.example.catalogoproducto.CatalogoProducto mapCatalogoProductoToResponse(
            CatalogoProducto catalogoProducto) {
        com.example.catalogoproducto.CatalogoProducto responseCatalogoProducto = new com.example.catalogoproducto.CatalogoProducto();
        responseCatalogoProducto.setId(String.valueOf(catalogoProducto.getId()));
        responseCatalogoProducto.setCatalogoId(catalogoProducto.getCatalogo().getId());
        responseCatalogoProducto.setNombreProducto(catalogoProducto.getProducto().getNombre());
        return responseCatalogoProducto;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCatalogoProductoByCatalogoRequest")
    @ResponsePayload
    public GetAllCatalogoProductoByCatalogoResponse getAllCatalogoProductoByCatalogo(
            @RequestPayload GetAllCatalogoProductoByCatalogoRequest request) {
        GetAllCatalogoProductoByCatalogoResponse response = new GetAllCatalogoProductoByCatalogoResponse();

        List<CatalogoProducto> catalogoProductos = catalogoProductoRepository.findByCatalogoId(request.getCatalogoId());
        for (CatalogoProducto catalogoProducto : catalogoProductos) {
            response.getCatalogoProducto().add(mapCatalogoProductoToResponse(catalogoProducto));
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCatalogoProductoByTiendaRequest")
    @ResponsePayload
    public GetAllCatalogoProductoByTiendaResponse getAllCatalogoProductoByTienda(
            @RequestPayload GetAllCatalogoProductoByTiendaRequest request) {
        GetAllCatalogoProductoByTiendaResponse response = new GetAllCatalogoProductoByTiendaResponse();

        List<CatalogoProducto> catalogoProductos = catalogoProductoRepository
                .findByCatalogoTiendaCodigo(request.getTiendaId());
        for (CatalogoProducto catalogoProducto : catalogoProductos) {
            response.getCatalogoProducto().add(mapCatalogoProductoToResponse(catalogoProducto));
        }

        return response;
    }

}
