package com.example.demo;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.annotation.PostConstruct;

@EnableTransactionManagement
@SpringBootApplication
public class DemoApplication {

    private final GrpcServer grpcServer;

    public DemoApplication(GrpcServer grpcServer) {
        this.grpcServer = grpcServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostConstruct
    public void startGrpcServer() throws InterruptedException, IOException {
        grpcServer.start();
        grpcServer.blockUntilShutdown();
    }
}
