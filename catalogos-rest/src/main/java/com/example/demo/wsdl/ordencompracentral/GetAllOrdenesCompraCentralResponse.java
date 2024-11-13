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
import java.util.ArrayList;
import java.util.List;


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
 *         <element name="ordenCompracentral" type="{http://www.example.com/ordenes_compracentral}ordenCompraCentral" maxOccurs="unbounded"/>
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
@XmlRootElement(name = "getAllOrdenesCompraCentralResponse")
public class GetAllOrdenesCompraCentralResponse {

    @XmlElement(required = true)
    protected List<OrdenCompraCentral> ordenCompra;
    private List<OrdenCompraCentral> ordenCompracentral;

    /**
     * Gets the value of the ordenCompraCentral property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the ordenCompra property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdenCompraCentral().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdenCompraCentral }
     * 
     * 
     * @return
     *     The value of the ordenCompraCentral property.
     */
    List<OrdenCompraCentral> OrdenCompraCentral;


    public List<OrdenCompraCentral> getOrdenCompraCentral() {
        
        if (OrdenCompraCentral == null) {
            ordenCompracentral = new ArrayList<>();
        }
        return this.ordenCompracentral;
    }

}
