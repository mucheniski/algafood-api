package com.algaworks.algafood.exception;

// TODO: refatorar todas essas classes para apenas uma mais genérica
public class GrupoNaoEncontradoException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;
	
	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);		
	}
	
	public GrupoNaoEncontradoException(Long id) {
		this(String.format("Não existe Grupo com id %d", id));
	}
}
