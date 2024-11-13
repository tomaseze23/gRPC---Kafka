// src/main/java/com/example/demo/service/OrdenCompraCentralService.java
package com.example.demo.service;

import com.example.demo.clientes.OrdenCompraCentralCliente;
import com.example.demo.wsdl.ordencompracentral.GetAllOrdenesCompraCentralResponse;
import com.example.demo.wsdl.ordencompracentral.GetOrdenCompraCentralResponse;
import com.example.demo.wsdl.ordencompracentral.OrdenCompraCentral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrdenCompraCentralService {

    @Autowired
    private OrdenCompraCentralCliente ordenCompraCentral;

    public List<OrdenCompraCentral> obtenerTodasLasOrdenesCompraCentral() {
        GetAllOrdenesCompraCentralResponse response = ordenCompraCentral.getAllOrdenesCompraCentral();
        return response.getOrdenCompraCentral();
    }

    
    public OrdenCompraCentral obtenerOrdenCompraCentralPorId(long id) {
        GetOrdenCompraCentralResponse response = ordenCompraCentral.getOrdenCompraCentral(id);
        return response.getOrdenCompraCentral();
    }

}
