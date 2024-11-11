// src/main/java/com/example/demo/service/ProductoService.java
package com.example.demo.service;

import com.example.demo.clientes.ProductoCliente;
import com.example.demo.wsdl.producto.GetAllProductosResponse;
import com.example.demo.wsdl.producto.GetProductoResponse;
import com.example.demo.wsdl.producto.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoCliente productoCliente;

    // Obtener un Producto por Código
    public Producto getProductoByCodigo(String codigo) {
        GetProductoResponse response = productoCliente.getProducto(codigo);
        return response != null ? response.getProducto() : null;
    }

    // Obtener todos los Productos
    public List<Producto> getAllProductos() {
        GetAllProductosResponse response = productoCliente.getAllProductos();
        return response != null ? response.getProducto() : null;
    }
}
