package com.algaworks.algafood.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntidadeNaoEncotradaException extends ResponseStatusException {
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncotradaException(HttpStatus status, String mensagem) {
		super(status, mensagem);
		// TODO Auto-generated constructor stub
	}

	public EntidadeNaoEncotradaException(String mensagem) {
		/*
		 * O this chama o construtor de cima que é o padrão.
		 * esse construtor indica que se eu instanciar a classe através dele,
		 * o código de erro padrão vai ser o 404 not found
		 * */
		this(HttpStatus.NOT_FOUND, mensagem);
	}

}
