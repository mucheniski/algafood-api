package com.algaworks.algafood.exception;

import com.algaworks.algafood.entity.FormaPagamento;
import com.algaworks.algafood.entity.Restaurante;

public class FormaPagamentoNaoValidadaException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoValidadaException(String message) {
		super(message);
	}
	
	public FormaPagamentoNaoValidadaException(Restaurante restaurante, FormaPagamento formaPagamentoRecebida) {
		this(String.format("O restaurante %s não aceita a forma de pagamento %s", restaurante.getNome(), formaPagamentoRecebida.getDescricao() ));
	}
	
}
