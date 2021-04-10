package com.algaworks.algafood.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel("Representa o resumo de um restaurante")
@Getter
@Setter
public class RestauranteResumoDTO {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Restaurante Tailandes")
	private String nome;

	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;

	private CozinhaDTO cozinha;
	
}
