package com.example.demo.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CatalogoDTO {
    private long id;
    private String tiendaId;
    private String nombreCatalogo;
    private LocalDateTime fechaCreacion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(String tiendaId) {
        this.tiendaId = tiendaId;
    }

    public String getNombreCatalogo() {
        return nombreCatalogo;
    }

    public void setNombreCatalogo(String nombreCatalogo) {
        this.nombreCatalogo = nombreCatalogo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
