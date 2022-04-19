package com.totalplay.auth.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String codigo;
	protected String mensaje;
	protected List<String> detalles;
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
