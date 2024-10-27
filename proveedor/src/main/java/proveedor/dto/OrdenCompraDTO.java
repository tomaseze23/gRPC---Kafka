package proveedor.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrdenCompraDTO {
    private String tienda_id;
    private String estado;
    private String observaciones;
    private List<ItemOrdenCompraDTO> items;
}
