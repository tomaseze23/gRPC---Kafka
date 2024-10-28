// src/main/java/com/example/demo/clientes/TiendaCliente.java
package com.example.demo.clientes;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.demo.wsdl.tienda.GetAllTiendasRequest;
import com.example.demo.wsdl.tienda.GetAllTiendasResponse;
import com.example.demo.wsdl.tienda.GetTiendaRequest;
import com.example.demo.wsdl.tienda.GetTiendaResponse;
import com.example.demo.wsdl.tienda.Tienda;
import org.springframework.stereotype.Component;

@Component
public class TiendaCliente extends WebServiceGatewaySupport {

    private static final String SOAP_ACTION = "";

    public GetTiendaResponse getTienda(String codigo) {
        GetTiendaRequest request = new GetTiendaRequest();
        request.setCodigo(codigo);

        GetTiendaResponse response = (GetTiendaResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

     public GetAllTiendasResponse getAllTiendas() {
        GetAllTiendasRequest request = new GetAllTiendasRequest();

        GetAllTiendasResponse response = (GetAllTiendasResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }
}
