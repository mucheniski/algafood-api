package com.algaworks.algafood.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;
	
	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);		
	}
	
	public PermissaoNaoEncontradaException(Long id) {
		this(String.format("Não existe Permissao com código %d", id));
	}
	
}
