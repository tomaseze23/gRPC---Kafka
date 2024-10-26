// src/main/java/com/example/demo/clientes/UsuarioCliente.java
package com.example.demo.clientes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.demo.wsdl.usuario.CreateUsuarioRequest;
import com.example.demo.wsdl.usuario.CreateUsuarioResponse;
import com.example.demo.wsdl.usuario.GetAllUsuariosRequest;
import com.example.demo.wsdl.usuario.GetAllUsuariosResponse;
import com.example.demo.wsdl.usuario.GetUsuarioRequest;
import com.example.demo.wsdl.usuario.GetUsuarioResponse;
import com.example.demo.wsdl.usuario.Usuario;

@Component
public class UsuarioCliente extends WebServiceGatewaySupport {

    // Constante para soapAction vacío según el WSDL
    private static final String SOAP_ACTION = "";

    /**
     * Obtener un Usuario por Nombre de Usuario
     */
    public GetUsuarioResponse getUsuario(String nombreUsuario) {
        GetUsuarioRequest request = new GetUsuarioRequest();
        request.setNombreUsuario(nombreUsuario);

        GetUsuarioResponse response = (GetUsuarioResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request, new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    /**
     * Obtener todos los Usuarios
     */
    public GetAllUsuariosResponse getAllUsuarios() {
        GetAllUsuariosRequest request = new GetAllUsuariosRequest();

        GetAllUsuariosResponse response = (GetAllUsuariosResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request, new SoapActionCallback(SOAP_ACTION));

        return response;
    }

    /**
     * Crear un nuevo Usuario
     */
    public CreateUsuarioResponse createUsuario(Usuario usuario) {
        CreateUsuarioRequest request = new CreateUsuarioRequest();
        request.setUsuario(usuario);

        CreateUsuarioResponse response = (CreateUsuarioResponse) getWebServiceTemplate()
                .marshalSendAndReceive(getDefaultUri(), request, new SoapActionCallback(SOAP_ACTION));

        return response;
    }
}
