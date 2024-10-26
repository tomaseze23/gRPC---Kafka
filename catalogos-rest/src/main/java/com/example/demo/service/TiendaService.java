// src/main/java/com/example/demo/service/TiendaService.java
package com.example.demo.service;

import com.example.demo.clientes.TiendaCliente;
import com.example.demo.wsdl.tienda.GetTiendaResponse;
import com.example.demo.wsdl.tienda.Tienda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TiendaService {

    @Autowired
    private TiendaCliente tiendaCliente;

    // Obtener una Tienda por Código
    public Tienda getTiendaByCodigo(String codigo) {
        GetTiendaResponse response = tiendaCliente.getTienda(codigo);
        return response != null ? response.getTienda() : null;
    }
}
