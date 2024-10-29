package proveedor.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private List<String> talles;
    private List<String> colores;
    private List<String> urls;
    private Integer cantidadStockProveedor;
}
