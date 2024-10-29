package com.example.demo.service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(ProductoService.BEAN_NAME)
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class ProductoService extends ProductoServiceGrpc.ProductoServiceImplBase {
    public final static String BEAN_NAME = "ProductoService";

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void createProducto(com.example.demo.service.Producto request,
            StreamObserver<com.example.demo.service.Producto> responseObserver) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setCodigo(request.getCodigo());
        producto.setTalle(request.getTalle());
        producto.setFoto(request.getFoto());
        producto.setColor(request.getColor());

        Producto savedProducto = productoRepository.save(producto);

        com.example.demo.service.Producto response = com.example.demo.service.Producto.newBuilder()
                .setId(savedProducto.getId())
                .setNombre(savedProducto.getNombre())
                .setCodigo(savedProducto.getCodigo())
                .setTalle(savedProducto.getTalle())
                .setFoto(savedProducto.getFoto())
                .setColor(savedProducto.getColor())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProducto(com.example.demo.service.Producto request,
            StreamObserver<com.example.demo.service.Producto> responseObserver) {
        productoRepository.findById(request.getId()).ifPresentOrElse(producto -> {
            com.example.demo.service.Producto response = com.example.demo.service.Producto.newBuilder()
                    .setId(producto.getId())
                    .setNombre(producto.getNombre())
                    .setCodigo(producto.getCodigo())
                    .setTalle(producto.getTalle())
                    .setFoto(producto.getFoto())
                    .setColor(producto.getColor())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            responseObserver.onError(new RuntimeException("Producto no encontrado"));
        });
    }

    @Autowired
    private EntityManager entityManager;

    @Override
    public void listProductos(EmptyRequest request, StreamObserver<ProductoList> responseObserver) {
        System.out.println("listProductos: usando EntityManager");

        Iterable<Producto> productos = entityManager.createQuery("SELECT p FROM Producto p", Producto.class)
                .getResultList();

        System.out.println("listProductos: " + productos);

        ProductoList response = ProductoList.newBuilder()
                .addAllProductos(StreamSupport.stream(productos.spliterator(), false)
                        .map(producto -> com.example.demo.service.Producto.newBuilder()
                                .setId(producto.getId())
                                .setNombre(producto.getNombre())
                                .setCodigo(producto.getCodigo())
                                .setTalle(producto.getTalle())
                                .setFoto(producto.getFoto())
                                .setColor(producto.getColor())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateProducto(com.example.demo.service.Producto request,
            StreamObserver<com.example.demo.service.Producto> responseObserver) {
        productoRepository.findById(request.getId()).ifPresentOrElse(producto -> {
            producto.setNombre(request.getNombre());
            producto.setCodigo(request.getCodigo());
            producto.setTalle(request.getTalle());
            producto.setFoto(request.getFoto());
            producto.setColor(request.getColor());

            Producto updatedProducto = productoRepository.save(producto);

            com.example.demo.service.Producto response = com.example.demo.service.Producto.newBuilder()
                    .setId(updatedProducto.getId())
                    .setNombre(updatedProducto.getNombre())
                    .setCodigo(updatedProducto.getCodigo())
                    .setTalle(updatedProducto.getTalle())
                    .setFoto(updatedProducto.getFoto())
                    .setColor(updatedProducto.getColor())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            responseObserver.onError(new RuntimeException("Producto no encontrado"));
        });
    }

    @Override
    public void deleteProducto(com.example.demo.service.Producto request,
            StreamObserver<com.example.demo.service.Producto> responseObserver) {
        productoRepository.findById(request.getId()).ifPresentOrElse(producto -> {
            productoRepository.delete(producto);

            com.example.demo.service.Producto response = com.example.demo.service.Producto.newBuilder()
                    .setId(producto.getId())
                    .setNombre(producto.getNombre())
                    .setCodigo(producto.getCodigo())
                    .setTalle(producto.getTalle())
                    .setFoto(producto.getFoto())
                    .setColor(producto.getColor())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            responseObserver.onError(new RuntimeException("Producto no encontrado"));
        });
    }
}
