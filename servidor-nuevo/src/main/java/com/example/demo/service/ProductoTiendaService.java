package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;
import com.example.demo.model.ProductoTienda;
import com.example.demo.model.Tienda;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.ProductoTiendaRepository;
import com.example.demo.repository.TiendaRepository;

import io.grpc.stub.StreamObserver;

@Service
public class ProductoTiendaService extends ProductoTiendaServiceGrpc.ProductoTiendaServiceImplBase {

    @Autowired
    private ProductoTiendaRepository productoTiendaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TiendaRepository tiendaRepository;

    @Override
public void createProductoTienda(com.example.demo.service.ProductoTienda request, StreamObserver<com.example.demo.service.ProductoTienda> responseObserver) {
    // Buscar el producto en el repositorio
    java.util.Optional<Producto> productoOpt = productoRepository.findById(request.getProductoId());
    if (productoOpt.isEmpty()) {
        responseObserver.onError(new RuntimeException("Producto no encontrado"));
        return;
    }

    // Buscar la tienda en el repositorio
    Optional<Tienda> tiendaOpt = tiendaRepository.findByCodigo(request.getTiendaId());
    if (tiendaOpt.isEmpty()) {
        responseObserver.onError(new RuntimeException("Tienda no encontrada"));
        return;
    }

    // Crear una entidad ProductoTienda desde el request
    ProductoTienda productoTienda = new ProductoTienda();
    productoTienda.setProducto(productoOpt.get());
    productoTienda.setTienda(tiendaOpt.get());
    productoTienda.setColor(request.getColor());
    productoTienda.setTalle(request.getTalle());
    productoTienda.setCantidad(request.getCantidad());

    // Guardar en la base de datos
    ProductoTienda savedProductoTienda = productoTiendaRepository.save(productoTienda);

    // Construir la respuesta
    com.example.demo.service.ProductoTienda response = com.example.demo.service.ProductoTienda.newBuilder()
            .setId(savedProductoTienda.getId())
            .setProductoId(savedProductoTienda.getProducto().getId())
            .setTiendaId(savedProductoTienda.getTienda().getCodigo())
            .setColor(savedProductoTienda.getColor())
            .setTalle(savedProductoTienda.getTalle())
            .setCantidad(savedProductoTienda.getCantidad())
            .build();

    // Enviar la respuesta al cliente
    responseObserver.onNext(response);
    responseObserver.onCompleted();
}


    @Override
    public void getProductoTienda(com.example.demo.service.ProductoTienda request, StreamObserver<com.example.demo.service.ProductoTienda> responseObserver) {
        // Buscar ProductoTienda por ID
        productoTiendaRepository.findById(request.getId()).ifPresentOrElse(productoTienda -> {
            // Construir la respuesta
            com.example.demo.service.ProductoTienda response = com.example.demo.service.ProductoTienda.newBuilder()
                    .setId(productoTienda.getId())
                    .setProductoId(productoTienda.getProducto().getId())
                    .setTiendaId(productoTienda.getTienda().getCodigo())
                    .setColor(productoTienda.getColor())
                    .setTalle(productoTienda.getTalle())
                    .setCantidad(productoTienda.getCantidad())
                    .build();

            // Enviar la respuesta al cliente
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            // Manejar el caso en que ProductoTienda no sea encontrado
            responseObserver.onError(new RuntimeException("ProductoTienda no encontrado"));
        });
    }

    @Override
    public void listProductoTiendas(ListProductoTiendasRequest request, StreamObserver<ProductoTiendaList> responseObserver) {
     /*    // Filtrar ProductoTienda por tienda_id si se especifica
        List<ProductoTienda> productos = productoTiendaRepository.findAllById(Long.valueOf(request.getTiendaId())).get();

        // Construir la respuesta
        ProductoTiendaList response = ProductoTiendaList.newBuilder()
            .addAllProductos(productos.stream().map(producto -> com.example.demo.service.ProductoTienda.newBuilder()
                    .setId(producto.getId())
                    .setProductoId(producto.getProducto().getId())
                    .setTiendaId(producto.getTienda().getCodigo())
                    .setColor(producto.getColor())
                    .setTalle(producto.getTalle())
                    .setCantidad(producto.getCantidad())
                    .build()).collect(Collectors.toList()))
            .build();


        // Enviar la respuesta al cliente
        responseObserver.onNext(response);
        responseObserver.onCompleted();*/
    }
}
