package com.algaworks.algafood.exception;

public class RestauranteNaoEncotradoException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncotradoException(String mensagem) {
		super(mensagem);		
	}
	
	public RestauranteNaoEncotradoException(Long restauranteId) {
		this(String.format("Não existe Restaurante com código %d", restauranteId));
	}
	
}
