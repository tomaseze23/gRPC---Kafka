package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.models.FiltroUsuario;
import com.example.demo.repository.FiltroUsuarioRepository;
import com.example.filtrousuario.GetFiltroUsuarioRequest;
import com.example.filtrousuario.GetFiltroUsuarioResponse;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Endpoint
public class FiltroUsuarioEndpoint {

    private static final String NAMESPACE_URI = "http://www.example.com/filtroUsuario";

    @Autowired
    private FiltroUsuarioRepository filtroUsuarioRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFiltroUsuarioRequest")
    @ResponsePayload
    public GetFiltroUsuarioResponse getFiltroUsuario(@RequestPayload GetFiltroUsuarioRequest request) {
        GetFiltroUsuarioResponse response = new GetFiltroUsuarioResponse();

        Optional<FiltroUsuario> filtroUsuarioOptional = filtroUsuarioRepository.findById(request.getId());

        if (filtroUsuarioOptional.isPresent()) {
            FiltroUsuario filtroUsuario = filtroUsuarioOptional.get();
            com.example.filtrousuario.FiltroUsuario filtroUsuarioResponse = new com.example.filtrousuario.FiltroUsuario();

            filtroUsuarioResponse.setId(filtroUsuario.getId());
            filtroUsuarioResponse.setUsuarioId(filtroUsuario.getUsuario().getId());
            filtroUsuarioResponse.setNombreFiltro(filtroUsuario.getNombreFiltro());
            filtroUsuarioResponse.setFiltro(filtroUsuario.getFiltro());

            if (filtroUsuario.getFechaCreacion() != null) {
                filtroUsuarioResponse.setFechaCreacion(convertToXMLGregorianCalendar(filtroUsuario.getFechaCreacion()));
            }

            response.setFiltroUsuario(filtroUsuarioResponse);
        }

        return response;
    }

    private XMLGregorianCalendar convertToXMLGregorianCalendar(OffsetDateTime dateTime) {
        try {
            if (dateTime == null) {
                return null;
            }
            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(dateTime.atZoneSameInstant(ZoneId.systemDefault()).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
