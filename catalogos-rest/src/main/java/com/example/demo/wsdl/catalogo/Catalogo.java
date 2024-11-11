//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.2 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package com.example.demo.wsdl.catalogo;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Catalogo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="Catalogo">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="tiendaId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="nombreCatalogo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="fechaCreacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Catalogo", propOrder = {
    "id",
    "tiendaId",
    "nombreCatalogo",
    "fechaCreacion"
})
public class Catalogo {

    protected long id;
    @XmlElement(required = true)
    protected String tiendaId;
    @XmlElement(required = true)
    protected String nombreCatalogo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaCreacion;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad tiendaId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTiendaId() {
        return tiendaId;
    }

    /**
     * Define el valor de la propiedad tiendaId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTiendaId(String value) {
        this.tiendaId = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreCatalogo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreCatalogo() {
        return nombreCatalogo;
    }

    /**
     * Define el valor de la propiedad nombreCatalogo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreCatalogo(String value) {
        this.nombreCatalogo = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaCreacion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Define el valor de la propiedad fechaCreacion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaCreacion(XMLGregorianCalendar value) {
        this.fechaCreacion = value;
    }

}
