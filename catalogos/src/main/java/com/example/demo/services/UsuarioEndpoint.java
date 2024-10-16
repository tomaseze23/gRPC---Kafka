package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.demo.models.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.usuarios.GetUsuarioRequest;
import com.example.usuarios.GetUsuarioResponse;

@Endpoint
public class UsuarioEndpoint {
   private static final String NAMESPACE_URI = "http://www.example.com/usuarios";

	@Autowired
	private UsuarioRepository usuarioRepository;


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUsuarioRequest")
	@ResponsePayload
	public GetUsuarioResponse getCountry(@RequestPayload GetUsuarioRequest request) {
		GetUsuarioResponse response = new GetUsuarioResponse();
		Usuario user = usuarioRepository.findByNombreUsuario(request.getNombreUsuario()).orElse(null);
		
		com.example.usuarios.Usuario usuario = new com.example.usuarios.Usuario();
		if(user != null){
			usuario.setId(user.getId());
			usuario.setNombre(user.getNombre());
			usuario.setApellido(user.getApellido());
			usuario.setContrasena(user.getContrasena());
			usuario.setHabilitado(user.isHabilitado());
			usuario.setTiendaId(user.getTienda().getCodigo());
			usuario.setNombreUsuario(user.getNombreUsuario());
			response.setUsuario(usuario);
		}
		response.setUsuario(usuario);

		return response;
	}

}
