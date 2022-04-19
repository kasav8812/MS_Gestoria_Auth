package com.totalplay.auth.model;

import java.io.Serializable;
import java.util.Base64;

import lombok.Data;

@Data
public class AuthModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private String pwacces;
	
	public AuthModel(String encode){
		byte[] decodedBytes = Base64.getDecoder().decode(encode.split(" ")[1]);
		String[] decodeUser = new String(decodedBytes).split(":");
		setUser(decodeUser[0]);
		setPwacces(decodeUser[1]);
	}
}
