//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.2 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package com.example.demo.wsdl.ordencompra;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.demo.wsdl.ordencompra package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.demo.wsdl.ordencompra
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetOrdenCompraRequest }
     * 
     * @return
     *     the new instance of {@link GetOrdenCompraRequest }
     */
    public GetOrdenCompraRequest createGetOrdenCompraRequest() {
        return new GetOrdenCompraRequest();
    }

    /**
     * Create an instance of {@link GetOrdenCompraResponse }
     * 
     * @return
     *     the new instance of {@link GetOrdenCompraResponse }
     */
    public GetOrdenCompraResponse createGetOrdenCompraResponse() {
        return new GetOrdenCompraResponse();
    }

    /**
     * Create an instance of {@link OrdenCompra }
     * 
     * @return
     *     the new instance of {@link OrdenCompra }
     */
    public OrdenCompra createOrdenCompra() {
        return new OrdenCompra();
    }

    /**
     * Create an instance of {@link GetAllOrdenesCompraRequest }
     * 
     * @return
     *     the new instance of {@link GetAllOrdenesCompraRequest }
     */
    public GetAllOrdenesCompraRequest createGetAllOrdenesCompraRequest() {
        return new GetAllOrdenesCompraRequest();
    }

    /**
     * Create an instance of {@link GetAllOrdenesCompraResponse }
     * 
     * @return
     *     the new instance of {@link GetAllOrdenesCompraResponse }
     */
    public GetAllOrdenesCompraResponse createGetAllOrdenesCompraResponse() {
        return new GetAllOrdenesCompraResponse();
    }

}
