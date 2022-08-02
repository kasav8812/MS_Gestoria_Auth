package com.totalplay.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.totalplay.auth.exception.ExceptionAPI;
import com.totalplay.auth.jwt.BuildJwt;
import com.totalplay.auth.model.AuthModel;
import com.totalplay.auth.model.JwtModel;
import com.totalplay.auth.service.UDetailService;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UDetailService userDetailService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BuildJwt buildJwt;

	@PostMapping
	public ResponseEntity<JwtModel> auth(@RequestHeader(name = "Authorization") String authorization) throws Exception {
		AuthModel auth = new AuthModel(authorization);
		authenticateUser(auth);
		final UserDetails userDatails = userDetailService.loadUserByUsername(auth.getUser());
		return ResponseEntity.status(HttpStatus.CREATED).body(buildJwt.getJwt(userDatails));
	}
	
	private void authenticateUser(AuthModel auth) throws Exception {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(auth.getUser(), auth.getPwacces()));
		} catch (DisabledException e) {
			throw new ExceptionAPI("Favor de agregar las credenciales", e, HttpStatus.BAD_REQUEST);
		} catch (BadCredentialsException e) {
			throw new ExceptionAPI("Credenciales Incorrectas", e, HttpStatus.BAD_REQUEST);
		}
	}
}
