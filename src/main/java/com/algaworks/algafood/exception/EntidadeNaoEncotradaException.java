package com.algaworks.algafood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncotradaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncotradaException(String mensagem) {
		super(mensagem);		
	}
}
