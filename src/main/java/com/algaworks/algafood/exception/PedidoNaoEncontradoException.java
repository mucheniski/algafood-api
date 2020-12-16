package com.algaworks.algafood.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;
	
	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);		
	}
	
	public PedidoNaoEncontradoException(Long id) {
		this(String.format("Não existe Pedido com código %d", id));
	}
	
}
