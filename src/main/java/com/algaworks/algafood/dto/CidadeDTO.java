package com.algaworks.algafood.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel(description = "Representa uma DTO de Cidade")
@Getter
@Setter
/*
* RepresentationModel é um container para representação de links
* ele contém modelos para que possam ser criados os links no projeto
* o link gerado por padrão já vem no formato HAL com um _links no retorno
* */
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

//	@ApiModelProperty(value = "id da Cidade", example = "1")
	@ApiModelProperty(example = "1",required = true)
	@NotNull
	private Long id;

	@ApiModelProperty(example = "Londrina", required = true)
	@NotBlank
	private String nome;

	private EstadoDTO estado;

}
