package com.algaworks.algafood.exception;

public class RestauranteNaoEncotradaException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;
	
	public RestauranteNaoEncotradaException(String mensagem) {
		super(mensagem);		
	}
	
	public RestauranteNaoEncotradaException(Long restauranteId) {
		this(String.format("Não existe Restaurante com código %d", restauranteId));
	}
	
}
