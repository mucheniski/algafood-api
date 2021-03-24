package com.algaworks.algafood.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "Representa uma DTO de Cidade")
@Getter
@Setter
public class CidadeDTO {	

//	@ApiModelProperty(value = "id da Cidade", example = "1")
	@ApiModelProperty(example = "1",required = true)
	@NotNull
	private Long id;

	@ApiModelProperty(example = "Londrina", required = true)
	@NotBlank
	private String nome;

	private EstadoDTO estado;

}
