package com.totalplay.auth.jwt;

import java.util.Date;
import java.util.HashMap;
import  java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.totalplay.auth.common.DateFormat;
import com.totalplay.auth.dao.UserDetailsDao;
import com.totalplay.auth.model.JwtModel;
import com.totalplay.auth.model.UserDetailsModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class BuildJwt {

	@Autowired
	private BuildKeys buildKeys;
	
	@Autowired
	private UserDetailsDao userDetailsDao;
	
	public JwtModel getJwt(UserDetails user) throws JwtException {
		try {
			UserDetailsModel userDetail = userDetailsDao.getUserInfo(user.getUsername());
			JwtModel jwt = new JwtModel();
			jwt.setToken(Jwts.builder().setHeaderParam("typ", "JWT").setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + 3600000))
					.setSubject(user.getUsername())
					.claim("name", userDetail.getSecretk())
					.claim("roles", userDetailsDao.getRoles(userDetail.getId()))
					.signWith(SignatureAlgorithm.RS512, buildKeys.getPrivateKey()).compact());
			jwt.setSuccess(true);
			jwt.setExpired(new DateFormat().formatoFecha.format(new Date(System.currentTimeMillis() + 3600000)));
			return jwt;
		} catch (Exception e) {
			throw new JwtException("Error al genrar el token");
		}
	}
	
	public Map<String, Object> validateJwt(String token) throws JwtException {
		try {
			Map<String, Object> result = new HashMap<>();
			Claims claims = (Claims) Jwts.parser().setSigningKey(buildKeys.getPublicKey()).parseClaimsJws(token).getBody();
			result.put("expiration", new DateFormat().formatoFecha.format(claims.getExpiration()));
			return result;
		} catch (Exception e) {
			throw new JwtException("Error al obteber el token");
		}
	}
}
