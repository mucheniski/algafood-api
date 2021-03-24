package com.algaworks.algafood.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "Representa uma DTO de Estado")
@Getter
@Setter
public class EstadoDTO {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;

	@ApiModelProperty(example = "Paraná")
	private String nome;
	
}
