package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.models.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.productos.GetAllProductosRequest;
import com.example.productos.GetAllProductosResponse;

@Endpoint
public class ProductoEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/productos";

    @Autowired
    private ProductoRepository productoRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductosRequest")
    @ResponsePayload
    public GetAllProductosResponse getAllProductos(@RequestPayload GetAllProductosRequest request) {
        GetAllProductosResponse response = new GetAllProductosResponse();

        List<Producto> productos = productoRepository.findAll();
        for (Producto producto : productos) {
            response.getProducto().add(mapProductoToResponse(producto));
        }

        return response;
    }

    private com.example.productos.Producto mapProductoToResponse(Producto producto) {
        com.example.productos.Producto responseProducto = new com.example.productos.Producto();
        responseProducto.setId(producto.getId());
        responseProducto.setNombre(producto.getNombre());
        responseProducto.setCodigo(producto.getCodigo());
        responseProducto.setTalle(producto.getTalle());
        responseProducto.setFoto(producto.getFoto());
        responseProducto.setColor(producto.getColor());
        responseProducto.setCantidadStockProveedor(producto.getCantidadStockProveedor());
        return responseProducto;
    }
}
