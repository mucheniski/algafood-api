package com.algaworks.algafood.exception;

public class CidadeNaoEncotradaException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;
	
	public CidadeNaoEncotradaException(String mensagem) {
		super(mensagem);		
	}
	
	public CidadeNaoEncotradaException(Long cidadeId) {
		this(String.format("Não existe Cidade com código %d", cidadeId));
	}
	
}
