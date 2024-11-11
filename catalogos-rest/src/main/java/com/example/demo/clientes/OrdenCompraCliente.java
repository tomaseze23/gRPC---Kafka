// src/main/java/com/example/demo/clientes/OrdenCompraCliente.java
package com.example.demo.clientes;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import com.example.demo.wsdl.ordencompra.GetAllOrdenesCompraRequest;
import com.example.demo.wsdl.ordencompra.GetAllOrdenesCompraResponse;
import com.example.demo.wsdl.ordencompra.GetOrdenCompraRequest;
import com.example.demo.wsdl.ordencompra.GetOrdenCompraResponse;
import org.springframework.stereotype.Component;

@Component
public class OrdenCompraCliente extends WebServiceGatewaySupport {

    // Constante para soapAction vacío según el WSDL
    private static final String SOAP_ACTION = "";

    // Obtener todas las órdenes de compra
    public GetAllOrdenesCompraResponse getAllOrdenesCompra() {
        GetAllOrdenesCompraRequest request = new GetAllOrdenesCompraRequest();

        GetAllOrdenesCompraResponse response = (GetAllOrdenesCompraResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    // Obtener una orden de compra por ID
    public GetOrdenCompraResponse getOrdenCompra(long id) {
        GetOrdenCompraRequest request = new GetOrdenCompraRequest();
        request.setId(id);

        GetOrdenCompraResponse response = (GetOrdenCompraResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }
}
