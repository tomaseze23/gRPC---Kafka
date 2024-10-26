// src/main/java/com/example/demo/clientes/TiendaCliente.java
package com.example.demo.clientes;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import com.example.demo.wsdl.tienda.GetTiendaRequest;
import com.example.demo.wsdl.tienda.GetTiendaResponse;
import com.example.demo.wsdl.tienda.Tienda;
import org.springframework.stereotype.Component;

@Component
public class TiendaCliente extends WebServiceGatewaySupport {

    // Constante para soapAction vacío según el WSDL
    private static final String SOAP_ACTION = "";

    // Obtener una Tienda por Código
    public GetTiendaResponse getTienda(String codigo) {
        GetTiendaRequest request = new GetTiendaRequest();
        request.setCodigo(codigo);

        GetTiendaResponse response = (GetTiendaResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }
}
