// src/main/java/com/example/demo/service/CatalogoProductoService.java
package com.example.demo.service;

import com.example.demo.clientes.CatalogoProductoCliente;
import com.example.demo.wsdl.catalogoproducto.CatalogoProducto;
import com.example.demo.wsdl.catalogoproducto.CreateCatalogoProductoResponse;
import com.example.demo.wsdl.catalogoproducto.DeleteCatalogoProductoResponse;
import com.example.demo.wsdl.catalogoproducto.GetAllCatalogoProductoByCatalogoResponse;
import com.example.demo.wsdl.catalogoproducto.GetAllCatalogoProductoByTiendaResponse;
import com.example.demo.wsdl.catalogoproducto.GetCatalogoProductoResponse;
import com.example.demo.wsdl.catalogoproducto.UpdateCatalogoProductoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoProductoService {

    @Autowired
    private CatalogoProductoCliente catalogoProductoCliente;

    public long crearCatalogoProducto(CatalogoProducto catalogoProducto) {
        CreateCatalogoProductoResponse response = catalogoProductoCliente.createCatalogoProducto(catalogoProducto);
        return response.getCatalogoProductoId();
    }

    public CatalogoProducto getCatalogoProducto(long id) {
        GetCatalogoProductoResponse response = catalogoProductoCliente.getCatalogoProducto(id);
        return response.getCatalogoProducto();
    }

    public List<CatalogoProducto> getAllCatalogoProductoByCatalogo(long catalogoId) {
        GetAllCatalogoProductoByCatalogoResponse response = catalogoProductoCliente.getAllCatalogoProductoByCatalogo(catalogoId);
        return response.getCatalogoProducto();
    }

    public List<CatalogoProducto> getAllCatalogoProductoByTienda(String tiendaId) {
        GetAllCatalogoProductoByTiendaResponse response = catalogoProductoCliente.getAllCatalogoProductoByTienda(tiendaId);
        return response.getCatalogoProducto();
    }

    public boolean updateCatalogoProducto(CatalogoProducto catalogoProducto) {
        UpdateCatalogoProductoResponse response = catalogoProductoCliente.updateCatalogoProducto(catalogoProducto);
        return response.isSuccess();
    }

    public boolean deleteCatalogoProducto(long id) {
        DeleteCatalogoProductoResponse response = catalogoProductoCliente.deleteCatalogoProducto(id);
        return response.isSuccess();
    }
}
