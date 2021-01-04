package com.algaworks.algafood.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.enuns.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

@JsonFilter("pedidoFiltro")
@Getter
@Setter
public class PedidoResumoDTO {
	
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private UsuarioRetornoDTO usuarioCliente;
	private RestauranteResumoDTO restaurante;
	private StatusPedido status;

}
