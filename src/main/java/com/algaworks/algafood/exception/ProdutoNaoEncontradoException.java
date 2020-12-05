package com.algaworks.algafood.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;
	
	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);		
	}
	
	public ProdutoNaoEncontradoException(Long id) {
		this(String.format("Não existe Produto com id %d", id));
	}
	
	public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
		this(String.format("Não existe Produto com id %d cadastrado para o Restaurante com id %d", produtoId, restauranteId));
	}
	
}
