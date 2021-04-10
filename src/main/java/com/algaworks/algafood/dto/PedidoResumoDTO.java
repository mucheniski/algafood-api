package com.algaworks.algafood.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.enuns.StatusPedido;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Representa um DTO com o Resumo de um pedido")
@Getter
@Setter
public class PedidoResumoDTO {

	@ApiModelProperty(example = "8e6588c2-b393-4e04-ba84-0d8a576977ae")
	private String codigo;

	@ApiModelProperty(example = "10.00")
	private BigDecimal subtotal;

	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;

	@ApiModelProperty(example = "15.00")
	private BigDecimal valorTotal;

	@ApiModelProperty(example = "2021-04-10T11:13:34Z")
	private OffsetDateTime dataCriacao;

	private UsuarioRetornoDTO usuarioCliente;

	private RestauranteResumoDTO restaurante;

	@ApiModelProperty(example = "CRIADO")
	private StatusPedido status;

}
