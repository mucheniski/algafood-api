package com.algaworks.algafood.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncotradaException {
	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
		this(String.format("Não existe Forma de Pagamento com o id %d", formaPagamentoId));
	}
	
}
