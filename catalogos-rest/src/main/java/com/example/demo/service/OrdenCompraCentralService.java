// src/main/java/com/example/demo/service/OrdenCompraCentralService.java
package com.example.demo.service;

import com.example.demo.clientes.OrdenCompraCentralCliente;
import com.example.demo.wsdl.ordencompra.GetAllOrdenesCompraCentralResponse;
import com.example.demo.wsdl.ordencompra.GetOrdenCompraCentralResponse;
import com.example.demo.wsdl.ordencompra.OrdenCompraCentral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenCompraCentralService {

    @Autowired
    private OrdenCompraCentralCliente ordenCompraCentralCliente;

    // Obtener todas las órdenes de compra central
    public List<OrdenCompraCentral> obtenerTodasLasOrdenesCompraCentral() {
        GetAllOrdenesCompraCentralResponse response = ordenCompraCentralCliente.getAllOrdenesCompraCentral();
        return response.getOrdenCompraCentral();
    }

    // Obtener una orden de compra central por ID
    public OrdenCompraCentral obtenerOrdenCompraCentralPorId(long id) {
        GetOrdenCompraCentralResponse response = ordenCompraCentralCliente.getOrdenCompraCentral(id);
        return response.getOrdenCompraCentral();
    }

    // Crear una nueva orden de compra central
    public OrdenCompraCentral crearOrdenCompraCentral(OrdenCompraCentral ordenCompraCentral) {
        // Lógica para crear una nueva orden, se puede implementar según el flujo
        // de negocio y como se haga la inserción de datos (por ejemplo, a través
        // de un cliente SOAP, base de datos, etc.)
        return ordenCompraCentralCliente.crearOrdenCompraCentral(ordenCompraCentral);
    }
}
