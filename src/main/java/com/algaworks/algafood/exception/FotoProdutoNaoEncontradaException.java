package com.algaworks.algafood.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FotoProdutoNaoEncontradaException(Long estadoId) {
		this(String.format("Não existe Foto para o produto com código %d", estadoId));
	}
	
}
