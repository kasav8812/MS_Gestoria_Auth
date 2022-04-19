package com.totalplay.auth.common;

import org.springframework.http.HttpStatus;

public class MessageException  extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final HttpStatus codeStatus;

	public MessageException(HttpStatus codeStatus, String message) {
		super(message);
		this.codeStatus = codeStatus;
	}

	public HttpStatus getCodeStatus() {
		return this.codeStatus;
	}

}
