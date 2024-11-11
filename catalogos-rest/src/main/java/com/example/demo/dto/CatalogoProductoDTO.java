// src/main/java/com/example/demo/dto/CatalogoProductoDTO.java
package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CatalogoProductoDTO {

    private String id;

    @NotNull(message = "El campo catalogoId es obligatorio")
    @Positive(message = "El campo catalogoId debe ser positivo")
    private Long catalogoId;

    @NotNull(message = "El campo nombreProducto es obligatorio")
    @Size(min = 1, max = 255, message = "El campo nombreProducto debe tener entre 1 y 255 caracteres")
    private String nombreProducto;

}
