//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.2 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package com.example.demo.wsdl.ordencompracentral;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ordenCompra complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="ordenCompra">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="tienda_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="codigo_articulo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="color" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="talle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="cantidad_solicitada" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ordenCompraCentral", propOrder = {
    "id",
    "tiendaId",
    "codigoArticulo",
    "Color",
    "Talle",
    "CantidadSolicitada"
})
public class OrdenCompraCentral {

    protected long id;
    @XmlElement(name = "tienda_id", required = true)
    protected String tiendaId;
    @XmlElement(name = "CodigoArticulo")
    protected String codigoArticulo;
    @XmlSchemaType(name = "Color")
    protected String color;
    @XmlElement(name = "Talle")
    protected String talle;
    @XmlElement(name = "CantidadSolicitada")
    protected Integer cantidadSolicitada;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return 
     */
    public long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     */
    public void setId(long value) {
        this.id = value;
    }




    /**
     * Obtiene el valor de la propiedad tiendaId.
     * 
     * @param codigoArticulo
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */

    public String getCodigoArticulo(String codigoArticulo) {
        return codigoArticulo;
    }

    /**
     * Define el valor de la propiedad tiendaId.
     * 
     * @param codigoArticulo
     *     {@link String }
     *     
     */
    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }


    public String getColor() {
        return color;
    }

    /**
     * 
     * @param color
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String color) {
        this.color = color;
    }

    
    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    /**
     * 
     * @param cantidadSolicitada
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }


    public String getTalle() {
        return talle;
    }

    /**
     * 
     * @param talle
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTalle(String talle) {
        this.color = talle;
    }

    public String getTiendaId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void setTiendaId(String tiendaId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public OrdenCompraCentral crearOrdenCompraCentral(OrdenCompraCentral ordenCompraCentral) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
