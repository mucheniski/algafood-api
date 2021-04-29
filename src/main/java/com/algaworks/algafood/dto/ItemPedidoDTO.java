package com.algaworks.algafood.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class ItemPedidoDTO extends RepresentationModel<ItemPedidoDTO> {
	
	private Long produtoId;
	private String produtoNome;
	private Integer quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;
	
}
