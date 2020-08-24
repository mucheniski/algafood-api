package com.algaworks.algafood.exception;

public abstract class EntidadeNaoEncotradaException extends NegocioException {
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncotradaException(String mensagem) {
		super(mensagem);		
	}
}
