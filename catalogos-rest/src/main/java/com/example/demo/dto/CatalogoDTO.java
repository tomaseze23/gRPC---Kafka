package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CatalogoDTO {
    private long id;
    private String tiendaId;
    private String nombreCatalogo;
    private LocalDateTime fechaCreacion;

}
