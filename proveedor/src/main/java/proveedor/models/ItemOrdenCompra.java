package proveedor.models;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "items_orden_compra")
@Data
public class ItemOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_compra_id", referencedColumnName = "id", nullable = false)
    private OrdenCompra ordenCompra;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id", nullable = false)
    private Producto producto;

    @Column(name = "color")
    private String color;

    @Column(name = "talle")
    private String talle;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;
}

