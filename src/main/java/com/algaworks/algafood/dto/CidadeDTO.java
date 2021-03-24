package com.algaworks.algafood.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Representa uma DTO de Cidade")
@Getter
@Setter
public class CidadeDTO {	

//	@ApiModelProperty(value = "id da Cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Londrina")
	private String nome;

	private EstadoDTO estado;

}
