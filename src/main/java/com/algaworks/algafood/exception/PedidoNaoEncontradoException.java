package com.algaworks.algafood.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;	
	
	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("Não existe Pedido com código %s", codigoPedido));
	}
	
}
