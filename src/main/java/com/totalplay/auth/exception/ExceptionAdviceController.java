package com.totalplay.auth.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.totalplay.auth.model.ErrorModel;

@RestControllerAdvice
public class ExceptionAdviceController {

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ErrorModel> missingRequestHeaderException(MissingRequestHeaderException e, WebRequest request) {
		ErrorModel error =  new ErrorModel();
		error.setCodigo("400");
		error.setMensaje("Petición mal formada");
		error.setDetalles(Arrays.asList("Header Requerido",e.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
