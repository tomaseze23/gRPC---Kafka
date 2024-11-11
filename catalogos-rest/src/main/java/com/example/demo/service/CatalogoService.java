// src/main/java/com/example/demo/service/CatalogoService.java
package com.example.demo.service;

import com.example.demo.clientes.CatalogoCliente;
import com.example.demo.wsdl.catalogo.Catalogo;
import com.example.demo.wsdl.catalogo.CreateCatalogoResponse;
import com.example.demo.wsdl.catalogo.DeleteCatalogoResponse;
import com.example.demo.wsdl.catalogo.GetAllCatalogosResponse;
import com.example.demo.wsdl.catalogo.GetCatalogoResponse;
import com.example.demo.wsdl.catalogo.UpdateCatalogoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {

    @Autowired
    private CatalogoCliente catalogoCliente;

    public long crearCatalogo(Catalogo catalogo) {
        CreateCatalogoResponse response = catalogoCliente.createCatalogo(catalogo);
        return response.getCatalogoId();
    }

    public Catalogo getCatalogo(long id) {
        GetCatalogoResponse response = catalogoCliente.getCatalogo(id);
        return response.getCatalogo();
    }
    public java.util.List<Catalogo> getAllCatalogos() {
        GetAllCatalogosResponse response = catalogoCliente.getAllCatalogos();
        return response.getCatalogo();
    }

    public boolean updateCatalogo(Catalogo catalogo) {
        UpdateCatalogoResponse response = catalogoCliente.updateCatalogo(catalogo);
        return response.isSuccess();
    }

    public boolean deleteCatalogo(long id) {
        DeleteCatalogoResponse response = catalogoCliente.deleteCatalogo(id);
        return response.isSuccess();
    }
}
