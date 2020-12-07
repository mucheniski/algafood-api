package com.algaworks.algafood.exception;

// TODO: transformar essa exception em generica para que todos chamem apenas ela
public abstract class EntidadeNaoEncotradaException extends NegocioException {
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncotradaException(String mensagem) {
		super(mensagem);		
	}
	
}
