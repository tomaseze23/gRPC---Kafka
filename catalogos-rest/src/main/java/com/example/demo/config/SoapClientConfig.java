// src/main/java/com/example/demo/config/SoapClientConfig.java
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.example.demo.clientes.CatalogoCliente;
import com.example.demo.clientes.CatalogoProductoCliente;
import com.example.demo.clientes.OrdenCompraCliente;
import com.example.demo.clientes.ProductoCliente;
import com.example.demo.clientes.TiendaCliente;
import com.example.demo.clientes.UsuarioCliente;

@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.example.demo.wsdl.catalogo", "com.example.demo.wsdl.catalogoproducto",
                "com.example.demo.wsdl.tienda", "com.example.demo.wsdl.producto", "com.example.demo.wsdl.ordencompra",
                "com.example.demo.wsdl.usuario");
        return marshaller;
    }

    @Bean
    public CatalogoCliente catalogoCliente(Jaxb2Marshaller marshaller) {
        CatalogoCliente client = new CatalogoCliente();
        client.setDefaultUri("http://localhost:8080/ws"); // Reemplaza con la URL real del servicio SOAP
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

    @Bean
    public CatalogoProductoCliente catalogoProductoCliente(Jaxb2Marshaller marshaller) {
        CatalogoProductoCliente client = new CatalogoProductoCliente();
        client.setDefaultUri("http://localhost:8080/ws"); // Reemplaza con la URL real del servicio SOAP
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

    @Bean
    public OrdenCompraCliente ordenCompraCliente(Jaxb2Marshaller marshaller) {
        OrdenCompraCliente client = new OrdenCompraCliente();
        client.setDefaultUri("http://localhost:8080/ws/ordenesCompra"); // Reemplaza con la URL real del servicio SOAP
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

    @Bean
    public ProductoCliente productoCliente(Jaxb2Marshaller marshaller) {
        ProductoCliente client = new ProductoCliente();
        client.setDefaultUri("http://localhost:8080/ws"); // Reemplaza con la URL real del servicio SOAP para Productos
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

    @Bean
    public TiendaCliente tiendaCliente(Jaxb2Marshaller marshaller) {
        TiendaCliente client = new TiendaCliente();
        client.setDefaultUri("http://localhost:8080/ws"); // Reemplaza con la URL real del servicio SOAP para Tiendas
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }

    @Bean
    public UsuarioCliente usuarioCliente(Jaxb2Marshaller marshaller) {
        UsuarioCliente client = new UsuarioCliente();
        client.setDefaultUri("http://localhost:8080/ws"); // Reemplaza con la URL real del servicio SOAP para Usuarios
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }
}
