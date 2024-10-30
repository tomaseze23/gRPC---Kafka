package com.stockearte.service;

import com.stockearte.model.Tienda;
import com.stockearte.repository.TiendaRepository;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.proto.service.*;

@Service
public class TiendaService extends TiendaServiceGrpc.TiendaServiceImplBase {

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void createTienda(com.proto.service.Tienda request,
            StreamObserver<com.proto.service.Tienda> responseObserver) {
        Tienda tienda = new Tienda();
        tienda.setCodigo(request.getCodigo());
        tienda.setDireccion(request.getDireccion());
        tienda.setCiudad(request.getCiudad());
        tienda.setProvincia(request.getProvincia());
        tienda.setHabilitada(request.getHabilitada());

        Tienda savedTienda = tiendaRepository.save(tienda);

        com.proto.service.Tienda response = com.proto.service.Tienda.newBuilder()
                .setCodigo(savedTienda.getCodigo())
                .setDireccion(savedTienda.getDireccion())
                .setCiudad(savedTienda.getCiudad())
                .setProvincia(savedTienda.getProvincia())
                .setHabilitada(savedTienda.isHabilitada())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTienda(com.proto.service.Tienda request,
            StreamObserver<com.proto.service.Tienda> responseObserver) {
        tiendaRepository.findById(request.getCodigo()).ifPresentOrElse(tienda -> {
            com.proto.service.Tienda response = com.proto.service.Tienda.newBuilder()
                    .setCodigo(tienda.getCodigo())
                    .setDireccion(tienda.getDireccion())
                    .setCiudad(tienda.getCiudad())
                    .setProvincia(tienda.getProvincia())
                    .setHabilitada(tienda.isHabilitada())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            responseObserver.onError(new RuntimeException("Tienda no encontrada"));
        });
    }

    @Override
    public void listTiendas(ListTiendasRequest request, StreamObserver<TiendaList> responseObserver) {

        System.out.println("Llegamos a tiendas: " + tiendaRepository);

        List<Tienda> tiendas = entityManager.createQuery("SELECT t FROM Tienda t", Tienda.class).getResultList();

        System.out.println("listTiendas: " + tiendas.size());

        // Construir la respuesta
        TiendaList response = TiendaList.newBuilder()
                .addAllTiendas(tiendas.stream().map(tienda -> com.proto.service.Tienda.newBuilder()
                        .setCodigo(tienda.getCodigo())
                        .setDireccion(tienda.getDireccion())
                        .setCiudad(tienda.getCiudad())
                        .setProvincia(tienda.getProvincia())
                        .setHabilitada(tienda.isHabilitada())
                        .build()).collect(Collectors.toList()))
                .build();

        // Enviar la respuesta al cliente
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateTienda(com.proto.service.Tienda request,
            StreamObserver<com.proto.service.Tienda> responseObserver) {
        tiendaRepository.findById(request.getCodigo()).ifPresentOrElse(tienda -> {
            tienda.setDireccion(request.getDireccion());
            tienda.setCiudad(request.getCiudad());
            tienda.setProvincia(request.getProvincia());
            tienda.setHabilitada(request.getHabilitada());

            Tienda updatedTienda = tiendaRepository.save(tienda);

            com.proto.service.Tienda response = com.proto.service.Tienda.newBuilder()
                    .setCodigo(updatedTienda.getCodigo())
                    .setDireccion(updatedTienda.getDireccion())
                    .setCiudad(updatedTienda.getCiudad())
                    .setProvincia(updatedTienda.getProvincia())
                    .setHabilitada(updatedTienda.isHabilitada())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            responseObserver.onError(new RuntimeException("Tienda no encontrada"));
        });
    }

    @Override
    public void deleteTienda(com.proto.service.Tienda request,
            StreamObserver<com.proto.service.Tienda> responseObserver) {
        tiendaRepository.findById(request.getCodigo()).ifPresentOrElse(tienda -> {
            tiendaRepository.delete(tienda);

            com.proto.service.Tienda response = com.proto.service.Tienda.newBuilder()
                    .setCodigo(tienda.getCodigo())
                    .setDireccion(tienda.getDireccion())
                    .setCiudad(tienda.getCiudad())
                    .setProvincia(tienda.getProvincia())
                    .setHabilitada(tienda.isHabilitada())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }, () -> {
            responseObserver.onError(new RuntimeException("Tienda no encontrada"));
        });
    }
}
