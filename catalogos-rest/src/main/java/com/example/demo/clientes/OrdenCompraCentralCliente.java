// src/main/java/com/example/demo/clientes/OrdenCompraCliente.java
package com.example.demo.clientes;

import com.example.demo.wsdl.ordencompracentral.GetAllOrdenesCompraCentralRequest;
import com.example.demo.wsdl.ordencompracentral.GetOrdenCompraCentralRequest;
import com.example.demo.wsdl.ordencompracentral.GetOrdenCompraCentralResponse;
import com.example.demo.wsdl.ordencompracentral.OrdenCompraCentral;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Component
public class OrdenCompraCentralCliente extends WebServiceGatewaySupport {

    // Constante para soapAction vacío según el WSDL
    private static final String SOAP_ACTION = "";

    // Obtener todas las órdenes de compra
    public GetAllOrdenesCompraCentralResponse getAllOrdenesCompraCentral() {
        GetAllOrdenesCompraCentralRequest request = new GetAllOrdenesCompraCentralRequest();

        GetAllOrdenesCompraCentralResponse response = (GetAllOrdenesCompraCentralResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    // Obtener una orden de compra por ID
    public GetOrdenCompraCentralResponse getOrdenCompra(long id) {
        GetOrdenCompraCentralRequest request = new GetOrdenCompraCentralRequest();
        request.setId(id);

        GetOrdenCompraCentralResponse response = (GetOrdenCompraCentralResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    public GetOrdenCompraCentralResponse getOrdenCompraCentral(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public OrdenCompraCentral crearOrdenCompraCentral(OrdenCompraCentral ordenCompraCentral) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
