//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.2 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package com.example.demo.wsdl.usuario;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.demo.wsdl.usuario package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.demo.wsdl.usuario
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetUsuarioRequest }
     * 
     * @return
     *     the new instance of {@link GetUsuarioRequest }
     */
    public GetUsuarioRequest createGetUsuarioRequest() {
        return new GetUsuarioRequest();
    }

    /**
     * Create an instance of {@link GetUsuarioResponse }
     * 
     * @return
     *     the new instance of {@link GetUsuarioResponse }
     */
    public GetUsuarioResponse createGetUsuarioResponse() {
        return new GetUsuarioResponse();
    }

    /**
     * Create an instance of {@link Usuario }
     * 
     * @return
     *     the new instance of {@link Usuario }
     */
    public Usuario createUsuario() {
        return new Usuario();
    }

    /**
     * Create an instance of {@link GetAllUsuariosRequest }
     * 
     * @return
     *     the new instance of {@link GetAllUsuariosRequest }
     */
    public GetAllUsuariosRequest createGetAllUsuariosRequest() {
        return new GetAllUsuariosRequest();
    }

    /**
     * Create an instance of {@link GetAllUsuariosResponse }
     * 
     * @return
     *     the new instance of {@link GetAllUsuariosResponse }
     */
    public GetAllUsuariosResponse createGetAllUsuariosResponse() {
        return new GetAllUsuariosResponse();
    }

    /**
     * Create an instance of {@link Usuarios }
     * 
     * @return
     *     the new instance of {@link Usuarios }
     */
    public Usuarios createUsuarios() {
        return new Usuarios();
    }

    /**
     * Create an instance of {@link CreateUsuarioRequest }
     * 
     * @return
     *     the new instance of {@link CreateUsuarioRequest }
     */
    public CreateUsuarioRequest createCreateUsuarioRequest() {
        return new CreateUsuarioRequest();
    }

    /**
     * Create an instance of {@link CreateUsuarioResponse }
     * 
     * @return
     *     the new instance of {@link CreateUsuarioResponse }
     */
    public CreateUsuarioResponse createCreateUsuarioResponse() {
        return new CreateUsuarioResponse();
    }

}
