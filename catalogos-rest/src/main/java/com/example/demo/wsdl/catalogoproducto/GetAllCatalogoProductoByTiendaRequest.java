//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.2 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package com.example.demo.wsdl.catalogoproducto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="tiendaId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "tiendaId"
})
@XmlRootElement(name = "getAllCatalogoProductoByTiendaRequest")
public class GetAllCatalogoProductoByTiendaRequest {

    @XmlElement(required = true)
    protected String tiendaId;

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

}
