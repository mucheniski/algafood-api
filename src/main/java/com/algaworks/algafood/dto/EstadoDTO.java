package com.algaworks.algafood.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Representa uma DTO de Estado")
@Getter
@Setter
public class EstadoDTO {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Paraná")
	private String nome;
	
}
