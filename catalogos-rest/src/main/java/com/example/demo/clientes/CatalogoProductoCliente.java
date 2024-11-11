// src/main/java/com/example/demo/clientes/CatalogoProductoCliente.java
package com.example.demo.clientes;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import com.example.demo.wsdl.catalogoproducto.CreateCatalogoProductoRequest;
import com.example.demo.wsdl.catalogoproducto.CreateCatalogoProductoResponse;
import com.example.demo.wsdl.catalogoproducto.DeleteCatalogoProductoRequest;
import com.example.demo.wsdl.catalogoproducto.DeleteCatalogoProductoResponse;
import com.example.demo.wsdl.catalogoproducto.GetAllCatalogoProductoByCatalogoRequest;
import com.example.demo.wsdl.catalogoproducto.GetAllCatalogoProductoByCatalogoResponse;
import com.example.demo.wsdl.catalogoproducto.GetAllCatalogoProductoByTiendaRequest;
import com.example.demo.wsdl.catalogoproducto.GetAllCatalogoProductoByTiendaResponse;
import com.example.demo.wsdl.catalogoproducto.GetCatalogoProductoRequest;
import com.example.demo.wsdl.catalogoproducto.GetCatalogoProductoResponse;
import com.example.demo.wsdl.catalogoproducto.UpdateCatalogoProductoRequest;
import com.example.demo.wsdl.catalogoproducto.UpdateCatalogoProductoResponse;
import com.example.demo.wsdl.catalogoproducto.CatalogoProducto;
import org.springframework.stereotype.Component;

@Component
public class CatalogoProductoCliente extends WebServiceGatewaySupport {

    // Constante para soapAction vacío según el WSDL
    private static final String SOAP_ACTION = "";

    // Crear un nuevo CatalogoProducto
    public CreateCatalogoProductoResponse createCatalogoProducto(CatalogoProducto catalogoProducto) {
        CreateCatalogoProductoRequest request = new CreateCatalogoProductoRequest();
        request.setCatalogoProducto(catalogoProducto);

        CreateCatalogoProductoResponse response = (CreateCatalogoProductoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    // Obtener un CatalogoProducto por ID
    public GetCatalogoProductoResponse getCatalogoProducto(long id) {
        GetCatalogoProductoRequest request = new GetCatalogoProductoRequest();
        request.setId(id);

        GetCatalogoProductoResponse response = (GetCatalogoProductoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    // Obtener todos los CatalogoProductos por Catalogo ID
    public GetAllCatalogoProductoByCatalogoResponse getAllCatalogoProductoByCatalogo(long catalogoId) {
        GetAllCatalogoProductoByCatalogoRequest request = new GetAllCatalogoProductoByCatalogoRequest();
        request.setCatalogoId(catalogoId);

        GetAllCatalogoProductoByCatalogoResponse response = (GetAllCatalogoProductoByCatalogoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    // Obtener todos los CatalogoProductos por Tienda ID
    public GetAllCatalogoProductoByTiendaResponse getAllCatalogoProductoByTienda(String tiendaId) {
        GetAllCatalogoProductoByTiendaRequest request = new GetAllCatalogoProductoByTiendaRequest();
        request.setTiendaId(tiendaId);

        GetAllCatalogoProductoByTiendaResponse response = (GetAllCatalogoProductoByTiendaResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    // Actualizar un CatalogoProducto existente
    public UpdateCatalogoProductoResponse updateCatalogoProducto(CatalogoProducto catalogoProducto) {
        UpdateCatalogoProductoRequest request = new UpdateCatalogoProductoRequest();
        request.setCatalogoProducto(catalogoProducto);

        UpdateCatalogoProductoResponse response = (UpdateCatalogoProductoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    // Eliminar un CatalogoProducto por ID
    public DeleteCatalogoProductoResponse deleteCatalogoProducto(long id) {
        DeleteCatalogoProductoRequest request = new DeleteCatalogoProductoRequest();
        request.setId(id);

        DeleteCatalogoProductoResponse response = (DeleteCatalogoProductoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }
}
