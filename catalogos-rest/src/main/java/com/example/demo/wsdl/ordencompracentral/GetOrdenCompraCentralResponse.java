//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.2 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package com.example.demo.wsdl.ordencompracentral;

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
 *         <element name="ordenCompra" type="{http://www.example.com/ordenes_compra}ordenCompra"/>
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
    "ordenCompraCentral"
})
@XmlRootElement(name = "getOrdenCompraCentralResponse")
public class GetOrdenCompraCentralResponse {

    @XmlElement(required = true)
    private OrdenCompraCentral ordenCompraCentral;

    public GetOrdenCompraCentralResponse(OrdenCompraCentral ordenCompraCentral) {
        this.ordenCompraCentral = ordenCompraCentral;
    }

    GetOrdenCompraCentralResponse() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    /**
     * Obtiene el valor de la propiedad ordenCompra.
     * 
     * @return
     *     possible object is
     *     {@link OrdenCompraCentral }
     *     
     */
    public OrdenCompraCentral getOrdenCompraCentral() {
        return ordenCompraCentral;
    }

    /**
     * Define el valor de la propiedad ordenCompra.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdenCompraCentral }
     *     
     */
    public void setOrdenCompra(OrdenCompraCentral value) {
        this.ordenCompraCentral = value;
    }

    public void setOrdenCompraCentral(OrdenCompraCentral ordenCompraCentral) {
        this.ordenCompraCentral = ordenCompraCentral;
    }

}
