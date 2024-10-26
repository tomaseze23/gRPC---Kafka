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
 *         <element name="catalogoProducto" type="{http://www.example.com/catalogoProducto}CatalogoProducto"/>
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
    "catalogoProducto"
})
@XmlRootElement(name = "updateCatalogoProductoRequest")
public class UpdateCatalogoProductoRequest {

    @XmlElement(required = true)
    protected CatalogoProducto catalogoProducto;

    /**
     * Obtiene el valor de la propiedad catalogoProducto.
     * 
     * @return
     *     possible object is
     *     {@link CatalogoProducto }
     *     
     */
    public CatalogoProducto getCatalogoProducto() {
        return catalogoProducto;
    }

    /**
     * Define el valor de la propiedad catalogoProducto.
     * 
     * @param value
     *     allowed object is
     *     {@link CatalogoProducto }
     *     
     */
    public void setCatalogoProducto(CatalogoProducto value) {
        this.catalogoProducto = value;
    }

}
