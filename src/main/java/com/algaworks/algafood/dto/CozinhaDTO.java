package com.algaworks.algafood.dto;

import com.algaworks.algafood.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Representa um DTO de Cozinha")
@Getter
@Setter
public class CozinhaDTO {

	@ApiModelProperty(example = "1", required = true)
	@JsonView(RestauranteView.Resumo.class)
	private Long id;

	@ApiModelProperty(example = "Cozinha brasileira", required = true)
	@JsonView(RestauranteView.Resumo.class)
	private String nome;

}
