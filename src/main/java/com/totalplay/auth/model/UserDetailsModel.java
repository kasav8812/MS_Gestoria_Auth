package com.totalplay.auth.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDetailsModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String secretk;
}
