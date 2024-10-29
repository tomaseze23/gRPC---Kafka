package com.example.demo.service;

import io.grpc.stub.StreamObserver;

import org.springframework.stereotype.Service;

import com.unla.stockearte.service.*;;

@Service
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        // Crear la respuesta
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Nos conectamos! " + request.getName())
                .build();

        // Enviar la respuesta al cliente
        responseObserver.onNext(reply);
        // Completar la llamada
        responseObserver.onCompleted();
    }
}
