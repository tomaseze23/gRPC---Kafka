package proveedor.dto;

import lombok.Data;

@Data
public class ItemOrdenCompraDTO {
    private String producto_id;
    private String color;
    private String talle;
    private int cantidad;
}