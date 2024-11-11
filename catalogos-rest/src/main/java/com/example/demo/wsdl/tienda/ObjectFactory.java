//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.2 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package com.example.demo.wsdl.tienda;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.demo.wsdl.tienda package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.demo.wsdl.tienda
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTiendaRequest }
     * 
     * @return
     *     the new instance of {@link GetTiendaRequest }
     */
    public GetTiendaRequest createGetTiendaRequest() {
        return new GetTiendaRequest();
    }

    /**
     * Create an instance of {@link GetTiendaResponse }
     * 
     * @return
     *     the new instance of {@link GetTiendaResponse }
     */
    public GetTiendaResponse createGetTiendaResponse() {
        return new GetTiendaResponse();
    }

    /**
     * Create an instance of {@link Tienda }
     * 
     * @return
     *     the new instance of {@link Tienda }
     */
    public Tienda createTienda() {
        return new Tienda();
    }

    /**
     * Create an instance of {@link GetAllTiendasRequest }
     * 
     * @return
     *     the new instance of {@link GetAllTiendasRequest }
     */
    public GetAllTiendasRequest createGetAllTiendasRequest() {
        return new GetAllTiendasRequest();
    }

    /**
     * Create an instance of {@link GetAllTiendasResponse }
     * 
     * @return
     *     the new instance of {@link GetAllTiendasResponse }
     */
    public GetAllTiendasResponse createGetAllTiendasResponse() {
        return new GetAllTiendasResponse();
    }

}
