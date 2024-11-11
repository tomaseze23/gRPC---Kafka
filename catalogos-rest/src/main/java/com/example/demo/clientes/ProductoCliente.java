// src/main/java/com/example/demo/clientes/ProductoCliente.java
package com.example.demo.clientes;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import com.example.demo.wsdl.producto.GetAllProductosRequest;
import com.example.demo.wsdl.producto.GetAllProductosResponse;
import com.example.demo.wsdl.producto.GetProductoRequest;
import com.example.demo.wsdl.producto.GetProductoResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductoCliente extends WebServiceGatewaySupport {

    // Constante para soapAction vacío según el WSDL
    private static final String SOAP_ACTION = "";

    // Obtener un Producto por Código
    public GetProductoResponse getProducto(String codigo) {
        GetProductoRequest request = new GetProductoRequest();
        request.setCodigo(codigo);

        GetProductoResponse response = (GetProductoResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    // Obtener todos los Productos
    public GetAllProductosResponse getAllProductos() {
        GetAllProductosRequest request = new GetAllProductosRequest();

        GetAllProductosResponse response = (GetAllProductosResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request,
                        new SoapActionCallback(SOAP_ACTION));

        return response;
    }
}
