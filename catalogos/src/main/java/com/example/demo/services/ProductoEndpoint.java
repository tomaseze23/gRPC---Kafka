package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.models.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.productos.GetProductoRequest;
import com.example.productos.GetProductoResponse;

@Endpoint
public class ProductoEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/productos";

    @Autowired
    private ProductoRepository productoRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductoRequest")
    @ResponsePayload
    public GetProductoResponse getProducto(@RequestPayload GetProductoRequest request) {
        GetProductoResponse response = new GetProductoResponse();
        Producto producto = productoRepository.findById(Long.valueOf(request.getCodigo())).orElse(null);

        com.example.productos.Producto productoResponse = new com.example.productos.Producto();
        if (producto != null) {
            productoResponse.setId(producto.getId());
            productoResponse.setNombre(producto.getNombre());
            productoResponse.setCodigo(producto.getCodigo());
            productoResponse.setTalle(producto.getTalle());
            productoResponse.setFoto(producto.getFoto());
            productoResponse.setColor(producto.getColor());
            productoResponse.setCantidadStockProveedor(producto.getCantidadStockProveedor());
            response.setProducto(productoResponse);
        }
        return response;
    }
}
