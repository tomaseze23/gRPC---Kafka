package proveedor.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tiendas")
@Data
public class Tienda {

    @Id
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "habilitada", nullable = false)
    private boolean habilitada = true;
}
