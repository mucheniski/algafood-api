package com.algaworks.algafood.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.entity.enuns.StatusPedido;

public class Pedido {
	
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataConfirmacao;
	private LocalDateTime dataCancelamento;
	private LocalDateTime dataEntrega;
	
	private Endereco endereco;
	
	private Restaurante restaurante;
		
	private FormaPagamento formaPagamento;
	
	private StatusPedido status;
	
	private List itens = new ArrayList<>();

}
