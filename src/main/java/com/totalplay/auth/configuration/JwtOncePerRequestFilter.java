package com.totalplay.auth.configuration;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.totalplay.auth.common.MessageException;
import com.totalplay.auth.jwt.BuildJwt;
import com.totalplay.auth.model.ErrorModel;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtOncePerRequestFilter extends OncePerRequestFilter{

	@Autowired
	private BuildJwt buildJwt;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {	
		if(request.getPathInfo().contains("/auth")) {	
			filterChain.doFilter(request, response);
			return;		
		}
		
		try {
			validarToken(request, response, filterChain);
			filterChain.doFilter(request, response);
		} catch (MessageException e) {
			crearResponseIncidencia(response, HttpStatus.INTERNAL_SERVER_ERROR, e, "No autenticado");
		}catch (IllegalArgumentException e) {
			crearResponseIncidencia(response, HttpStatus.INTERNAL_SERVER_ERROR, e, "Token no encontrado");
		} catch (ExpiredJwtException e) {
			crearResponseIncidencia(response, HttpStatus.INTERNAL_SERVER_ERROR, e, "Sesion finalizada");
		}
		
		
	}

	private void validarToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws MessageException, IllegalArgumentException, ExpiredJwtException {
		final String requestTokenHeader = request.getHeader("x-token");
		if(requestTokenHeader== null || requestTokenHeader.isEmpty()) {
			throw new MessageException(HttpStatus.INTERNAL_SERVER_ERROR, "El Token es requerido");
		}
		
		try {
			buildJwt.validateJwt(requestTokenHeader);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Error al obtener el token");
		} catch (ExpiredJwtException e) {
			throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "Token expiro");
		}
	}

	private static void crearResponseIncidencia(HttpServletResponse response, HttpStatus httpStatus, Exception ex,
			String mensaje) {
		ErrorModel error = new ErrorModel("totalplay-" + httpStatus.value(), mensaje, Arrays.asList(ex.getMessage()));
		response.setContentType("application/json");
		response.setStatus(httpStatus.value());
		try {
			response.getOutputStream().print(error.toString());
		} catch (IOException e) {
			
		}
	}
}
