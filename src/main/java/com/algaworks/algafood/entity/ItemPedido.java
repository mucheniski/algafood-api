package com.algaworks.algafood.entity;

import java.math.BigDecimal;

public class ItemPedido {
	
	private Integer quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;
	
	private Produto produto;
	
	private Pedido pedido;

}
