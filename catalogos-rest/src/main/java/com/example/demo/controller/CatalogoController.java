// src/main/java/com/example/demo/controller/CatalogoController.java
package com.example.demo.controller;

import com.example.demo.dto.CatalogoDTO;
import com.example.demo.service.CatalogoService;
import com.example.demo.service.PdfExportService;
import com.example.demo.util.DateConverter;
import com.example.demo.wsdl.catalogo.Catalogo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalogo")
@Tag(name = "Catálogo", description = "Operaciones relacionadas con el catálogo")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private PdfExportService pdfExportService;

    @Operation(summary = "Crear un nuevo catálogo", description = "Crea un nuevo catálogo enviando una solicitud al servicio SOAP")
    @PostMapping("/crear")
    public ResponseEntity<Long> crearCatalogo(@RequestBody CatalogoDTO catalogoDTO) {
        Catalogo catalogo = new Catalogo();
        catalogo.setId(catalogoDTO.getId());
        catalogo.setTiendaId(catalogoDTO.getTiendaId());
        catalogo.setNombreCatalogo(catalogoDTO.getNombreCatalogo());
        catalogo.setFechaCreacion(DateConverter.toXMLGregorianCalendar(catalogoDTO.getFechaCreacion()));
        long catalogoId = catalogoService.crearCatalogo(catalogo);
        return ResponseEntity.ok(catalogoId);
    }

    @Operation(summary = "Obtener un catálogo por ID", description = "Obtiene un catálogo específico utilizando su ID")
    @GetMapping("/{id}")
    public ResponseEntity<CatalogoDTO> getCatalogo(@PathVariable long id) {
        Catalogo catalogo = catalogoService.getCatalogo(id);
        if (catalogo != null) {
            CatalogoDTO catalogoDTO = new CatalogoDTO();
            catalogoDTO.setId(catalogo.getId());
            catalogoDTO.setTiendaId(catalogo.getTiendaId());
            catalogoDTO.setNombreCatalogo(catalogo.getNombreCatalogo());
            catalogoDTO.setFechaCreacion(DateConverter.fromXMLGregorianCalendar(catalogo.getFechaCreacion()));
            return ResponseEntity.ok(catalogoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener todos los catálogos", description = "Obtiene una lista de todos los catálogos disponibles")
    @GetMapping
    public ResponseEntity<List<CatalogoDTO>> getAllCatalogos() {
        List<Catalogo> catalogos = catalogoService.getAllCatalogos();
        List<CatalogoDTO> catalogoDTOs = catalogos.stream().map(catalogo -> {
            CatalogoDTO dto = new CatalogoDTO();
            dto.setId(catalogo.getId());
            dto.setTiendaId(catalogo.getTiendaId());
            dto.setNombreCatalogo(catalogo.getNombreCatalogo());
            dto.setFechaCreacion(DateConverter.fromXMLGregorianCalendar(catalogo.getFechaCreacion()));
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(catalogoDTOs);
    }

    @Operation(summary = "Actualizar un catálogo", description = "Actualiza un catálogo existente enviando una solicitud al servicio SOAP")
    @PutMapping("/actualizar")
    public ResponseEntity<Boolean> updateCatalogo(@RequestBody CatalogoDTO catalogoDTO) {
        Catalogo catalogo = new Catalogo();
        catalogo.setId(catalogoDTO.getId());
        catalogo.setTiendaId(catalogoDTO.getTiendaId());
        catalogo.setNombreCatalogo(catalogoDTO.getNombreCatalogo());
        catalogo.setFechaCreacion(DateConverter.toXMLGregorianCalendar(catalogoDTO.getFechaCreacion()));
        boolean success = catalogoService.updateCatalogo(catalogo);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "Eliminar un catálogo", description = "Elimina un catálogo específico utilizando su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCatalogo(@PathVariable long id) {
        boolean success = catalogoService.deleteCatalogo(id);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "Exportar catálogo a PDF", description = "Genera y exporta un PDF del catálogo específico")
    @GetMapping("/{id}/export-pdf")
    public void exportCatalogToPDF(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename("catalogo_" + id + ".pdf")
                .build();

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        try {
            pdfExportService.exportCatalogToPDF(id, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el PDF");
        }
    }
}
