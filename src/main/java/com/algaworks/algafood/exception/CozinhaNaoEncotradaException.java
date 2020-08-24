package com.algaworks.algafood.exception;

public class CozinhaNaoEncotradaException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;
	
	public CozinhaNaoEncotradaException(String mensagem) {
		super(mensagem);		
	}
	
	public CozinhaNaoEncotradaException(Long cozinhaId) {
		this(String.format("Não existe Cozinha com código %d", cozinhaId));
	}
	
}
