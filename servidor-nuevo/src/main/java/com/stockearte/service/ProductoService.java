package com.stockearte.service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.proto.service.EmptyRequest;
import com.proto.service.ProductoList;
import com.proto.service.ProductoServiceGrpc;
import com.stockearte.model.Producto;
import com.stockearte.repository.ProductoRepository;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.stockearte.data.ProductoDataService;

@Service
public class ProductoService extends ProductoServiceGrpc.ProductoServiceImplBase {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoDataService productoDataService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createProducto(com.proto.service.Producto request,
            StreamObserver<com.proto.service.Producto> responseObserver) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setCodigo(request.getCodigo());
        producto.setTalle(request.getTalle());
        producto.setFoto(request.getFoto());
        producto.setColor(request.getColor());

        Producto savedProducto = productoRepository.save(producto);

        com.proto.service.Producto response = com.proto.service.Producto.newBuilder()
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
    public void getProducto(com.proto.service.Producto request,
            StreamObserver<com.proto.service.Producto> responseObserver) {
        productoRepository.findById(request.getId()).ifPresentOrElse(producto -> {
            com.proto.service.Producto response = com.proto.service.Producto.newBuilder()
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

    @Override
    public void listProductos(EmptyRequest request, StreamObserver<ProductoList> responseObserver) {
        System.out.println("listProductos: usando EntityManager");

        Iterable<Producto> productos = entityManager.createQuery("SELECT p FROM Producto p", Producto.class)
                .getResultList();

        System.out.println("listProductos: " + productos);

        ProductoList response = ProductoList.newBuilder()
                .addAllProductos(StreamSupport.stream(productos.spliterator(), false)
                        .map(producto -> com.proto.service.Producto.newBuilder()
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
    public void updateProducto(com.proto.service.Producto request,
            StreamObserver<com.proto.service.Producto> responseObserver) {
        productoRepository.findById(request.getId()).ifPresentOrElse(producto -> {
            producto.setNombre(request.getNombre());
            producto.setCodigo(request.getCodigo());
            producto.setTalle(request.getTalle());
            producto.setFoto(request.getFoto());
            producto.setColor(request.getColor());

            Producto updatedProducto = productoRepository.save(producto);

            com.proto.service.Producto response = com.proto.service.Producto.newBuilder()
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

    @Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
    "java.lang.Exception" }, propagation = Propagation.REQUIRED)
    @Override
    public void deleteProducto(com.proto.service.Producto request,
            StreamObserver<com.proto.service.Producto> responseObserver) {

        System.out.println("deleteProduct: " + request.getId());

        try {
            productoDataService.deleteProductoById(request.getId());
            System.out.println("deleteProduct: OK");

            // Construir la respuesta
            com.proto.service.Producto response = com.proto.service.Producto.newBuilder()
                    .setId(request.getId())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            responseObserver.onError(new RuntimeException("Error al eliminar el producto: " + e.getMessage()));
        }
    }

}
