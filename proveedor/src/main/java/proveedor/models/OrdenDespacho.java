package proveedor.models;

import lombok.Data;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "ordenes_despacho")
@Data
public class OrdenDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_compra_id", referencedColumnName = "id", nullable = false)
    private OrdenCompra ordenCompra;

    @Column(name = "fecha_estimacion_envio")
    private OffsetDateTime fechaEstimacionEnvio;

    @Column(name = "estado", nullable = false)
    private String estado;
}
