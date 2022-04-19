package com.totalplay.auth.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean success;
	private String expired;
	private String token;
}
