package com.algaworks.algafood.exception;

import com.algaworks.algafood.entity.Produto;
import com.algaworks.algafood.entity.Restaurante;

public class ProdutoNaoValidadoException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public ProdutoNaoValidadoException(String message) {
		super(message);
	}
	
	public ProdutoNaoValidadoException(Produto produto, Restaurante restaurante) {
		this(String.format("O produto %d não pertence ao restaurante %d", produto.getDescricao(), restaurante.getNome()));
	}

}
