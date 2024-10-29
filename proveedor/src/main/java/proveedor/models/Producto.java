package proveedor.models;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;

    @Column(name = "talle")
    private String talle;

    @Column(name = "foto")
    private String foto;

    @Column(name = "color")
    private String color;

    @Column(name = "cantidad_stock_proveedor", nullable = false)
    private int cantidadStockProveedor = 0;
}

