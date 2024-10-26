// src/main/java/com/example/demo/clientes/CatalogoCliente.java
package com.example.demo.clientes;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import com.example.demo.wsdl.catalogo.CreateCatalogoRequest;
import com.example.demo.wsdl.catalogo.CreateCatalogoResponse;
import com.example.demo.wsdl.catalogo.DeleteCatalogoRequest;
import com.example.demo.wsdl.catalogo.DeleteCatalogoResponse;
import com.example.demo.wsdl.catalogo.GetAllCatalogosRequest;
import com.example.demo.wsdl.catalogo.GetAllCatalogosResponse;
import com.example.demo.wsdl.catalogo.GetCatalogoRequest;
import com.example.demo.wsdl.catalogo.GetCatalogoResponse;
import com.example.demo.wsdl.catalogo.UpdateCatalogoRequest;
import com.example.demo.wsdl.catalogo.UpdateCatalogoResponse;
import com.example.demo.wsdl.catalogo.Catalogo;
import org.springframework.stereotype.Component;

@Component
public class CatalogoCliente extends WebServiceGatewaySupport {

    private static final String SOAP_ACTION = "";

    public CreateCatalogoResponse createCatalogo(Catalogo catalogo) {
        CreateCatalogoRequest request = new CreateCatalogoRequest();
        request.setCatalogo(catalogo);

        CreateCatalogoResponse response = (CreateCatalogoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    public GetCatalogoResponse getCatalogo(long id) {
        GetCatalogoRequest request = new GetCatalogoRequest();
        request.setId(id);

        GetCatalogoResponse response = (GetCatalogoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    public GetAllCatalogosResponse getAllCatalogos() {
        GetAllCatalogosRequest request = new GetAllCatalogosRequest();

        GetAllCatalogosResponse response = (GetAllCatalogosResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    public UpdateCatalogoResponse updateCatalogo(Catalogo catalogo) {
        UpdateCatalogoRequest request = new UpdateCatalogoRequest();
        request.setCatalogo(catalogo);

        UpdateCatalogoResponse response = (UpdateCatalogoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    public DeleteCatalogoResponse deleteCatalogo(long id) {
        DeleteCatalogoRequest request = new DeleteCatalogoRequest();
        request.setId(id);

        DeleteCatalogoResponse response = (DeleteCatalogoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }
}
