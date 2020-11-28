package com.algaworks.algafood.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);		
	}
	
	public UsuarioNaoEncontradoException(Long id) {
		this(String.format("Não existe Usuario com id %d", id));
	}
	
}
