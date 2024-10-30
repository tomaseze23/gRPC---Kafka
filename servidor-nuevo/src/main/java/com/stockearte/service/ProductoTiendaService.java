package com.stockearte.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockearte.model.Producto;
import com.stockearte.model.ProductoTienda;
import com.stockearte.model.Tienda;
import com.stockearte.repository.ProductoRepository;
import com.stockearte.repository.ProductoTiendaRepository;
import com.stockearte.repository.TiendaRepository;

import io.grpc.stub.StreamObserver;
import com.proto.service.*;

@Service
public class ProductoTiendaService extends ProductoTiendaServiceGrpc.ProductoTiendaServiceImplBase {

    @Autowired
    private ProductoTiendaRepository productoTiendaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TiendaRepository tiendaRepository;

    @Override
public void createProductoTienda(com.proto.service.ProductoTienda request, StreamObserver<com.proto.service.ProductoTienda> responseObserver) {
    java.util.Optional<Producto> productoOpt = productoRepository.findById(request.getProductoId());
    if (productoOpt.isEmpty()) {
        responseObserver.onError(new RuntimeException("Producto no encontrado"));
        return;
    }

    Optional<Tienda> tiendaOpt = tiendaRepository.findByCodigo(request.getTiendaId());
    if (tiendaOpt.isEmpty()) {
        responseObserver.onError(new RuntimeException("Tienda no encontrada"));
        return;
    }

    ProductoTienda productoTienda = new ProductoTienda();
    productoTienda.setProducto(productoOpt.get());
    productoTienda.setTienda(tiendaOpt.get());
    productoTienda.setColor(request.getColor());
    productoTienda.setTalle(request.getTalle());
    productoTienda.setCantidad(request.getCantidad());

    ProductoTienda savedProductoTienda = productoTiendaRepository.save(productoTienda);

    com.proto.service.ProductoTienda response = com.proto.service.ProductoTienda.newBuilder()
            .setId(savedProductoTienda.getId())
            .setProductoId(savedProductoTienda.getProducto().getId())
            .setTiendaId(savedProductoTienda.getTienda().getCodigo())
            .setColor(savedProductoTienda.getColor())
            .setTalle(savedProductoTienda.getTalle())
            .setCantidad(savedProductoTienda.getCantidad())
            .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
}


    @Override
    public void getProductoTienda(com.proto.service.ProductoTienda request, StreamObserver<com.proto.service.ProductoTienda> responseObserver) {
        productoTiendaRepository.findById(request.getId()).ifPresentOrElse(productoTienda -> {
            com.proto.service.ProductoTienda response = com.proto.service.ProductoTienda.newBuilder()
                    .setId(productoTienda.getId())
                    .setProductoId(productoTienda.getProducto().getId())
                    .setTiendaId(productoTienda.getTienda().getCodigo())
                    .setColor(productoTienda.getColor())
                    .setTalle(productoTienda.getTalle())
                    .setCantidad(productoTienda.getCantidad())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            responseObserver.onError(new RuntimeException("ProductoTienda no encontrado"));
        });
    }

    @Override
    public void listProductoTiendas(ListProductoTiendasRequest request, StreamObserver<ProductoTiendaList> responseObserver) {
        List<ProductoTienda> productos = productoTiendaRepository.findAll();

        ProductoTiendaList response = ProductoTiendaList.newBuilder()
            .addAllProductos(productos.stream().map(producto -> com.proto.service.ProductoTienda.newBuilder()
                    .setId(producto.getId())
                    .setProductoId(producto.getProducto().getId())
                    .setTiendaId(producto.getTienda().getCodigo())
                    .setColor(producto.getColor())
                    .setTalle(producto.getTalle())
                    .setCantidad(producto.getCantidad())
                    .build()).collect(Collectors.toList()))
            .build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
