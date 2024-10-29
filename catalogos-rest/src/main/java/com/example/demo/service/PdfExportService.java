// src/main/java/com/example/demo/service/PdfExportService.java
package com.example.demo.service;

import com.example.demo.clientes.CatalogoCliente;
import com.example.demo.clientes.CatalogoProductoCliente;
import com.example.demo.wsdl.catalogo.GetCatalogoResponse;
import com.example.demo.wsdl.catalogoproducto.GetAllCatalogoProductoByCatalogoResponse;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PdfExportService {

    @Autowired
    private CatalogoCliente catalogoCliente;

    @Autowired
    private CatalogoProductoCliente catalogoProductoCliente;

    public void exportCatalogToPDF(Long catalogoId, jakarta.servlet.http.HttpServletResponse response) throws IOException, DocumentException {
        GetCatalogoResponse catalogoResponse = catalogoCliente.getCatalogo(catalogoId);
        GetAllCatalogoProductoByCatalogoResponse productosResponse = catalogoProductoCliente.getAllCatalogoProductoByCatalogo(catalogoId);

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph("Catalogo: " + catalogoResponse.getCatalogo().getNombreCatalogo()));
        document.add(new Paragraph("Fecha de Creación: " + catalogoResponse.getCatalogo().getFechaCreacion()));

        PdfPTable table = new PdfPTable(2); 
        table.addCell("Nombre del Producto");
        table.addCell("ID del Producto");

        productosResponse.getCatalogoProducto().forEach(producto -> {
            table.addCell(producto.getNombreProducto());
            table.addCell(String.valueOf(producto.getId()));
        });

        document.add(table);
        document.close();
    }
}
