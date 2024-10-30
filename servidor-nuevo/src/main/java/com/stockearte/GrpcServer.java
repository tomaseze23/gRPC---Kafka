package com.stockearte;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.stockearte.service.GreeterImpl;
import com.stockearte.service.ProductoService;
import com.stockearte.service.ProductoTiendaService;
import com.stockearte.service.TiendaService;
import com.stockearte.service.UsuarioService;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@Component
public class GrpcServer {
    private Server server;

    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class);

    @Autowired
    private GreeterImpl greeterService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TiendaService tiendaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoTiendaService productoTiendaService;

    public void start() throws IOException {
        server = ServerBuilder.forPort(9090)
                .addService(greeterService)
                .addService(usuarioService)
                .addService(tiendaService)
                .addService(productoService)
                .addService(productoTiendaService)
                .build()
                .start();
        logger.info("Servidor gRPC iniciado en el puerto 9090...");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Apagando servidor gRPC...");
            GrpcServer.this.stop();
            logger.info("Servidor gRPC apagado.");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();
        server.start();
        server.blockUntilShutdown();
    }
}
