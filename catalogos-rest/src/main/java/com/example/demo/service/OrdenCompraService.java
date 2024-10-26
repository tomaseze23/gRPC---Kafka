// src/main/java/com/example/demo/service/OrdenCompraService.java
package com.example.demo.service;

import com.example.demo.clientes.OrdenCompraCliente;
import com.example.demo.wsdl.ordencompra.GetAllOrdenesCompraResponse;
import com.example.demo.wsdl.ordencompra.GetOrdenCompraResponse;
import com.example.demo.wsdl.ordencompra.OrdenCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraCliente ordenCompraCliente;

    // Obtener todas las órdenes de compra
    public List<OrdenCompra> obtenerTodasLasOrdenesCompra() {
        GetAllOrdenesCompraResponse response = ordenCompraCliente.getAllOrdenesCompra();
        return response.getOrdenCompra();
    }

    // Obtener una orden de compra por ID
    public OrdenCompra obtenerOrdenCompraPorId(long id) {
        GetOrdenCompraResponse response = ordenCompraCliente.getOrdenCompra(id);
        return response.getOrdenCompra();
    }
}
