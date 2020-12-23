package com.algaworks.algafood.enuns;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);
	
	private String descricao;
	private List<StatusPedido> statusAnteriores;
	
	/*
	 * É usado o varArgs StatusPedido... statusAnteriores porque pode ser passado nenhum, ou mais do que um.
	 */
	private StatusPedido(String descricao, StatusPedido... statusAnteriores) {
		this.descricao = descricao;
		this.statusAnteriores = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean podeAlterarPara(StatusPedido novoStatus) {
		/*
		 * O this é o status que está sendo passado, por exemplo, se na chamada o status passado for CANCELADO o this é esse valor
		 * no caso a lista de statusAnterioes vai conter o status CRIADO.
		 */
		return novoStatus.statusAnteriores.contains(this);
	}
	
}
