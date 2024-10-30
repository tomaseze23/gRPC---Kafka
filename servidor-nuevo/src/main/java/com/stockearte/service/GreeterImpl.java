package com.stockearte.service;

import io.grpc.stub.StreamObserver;

import org.springframework.stereotype.Service;

import com.proto.service.*;

@Service
public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Nos conectamos! " + request.getName())
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
