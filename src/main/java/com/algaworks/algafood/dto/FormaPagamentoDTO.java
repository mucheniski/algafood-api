package com.algaworks.algafood.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@ApiModel(description = "Representa uma DTO de FormaPagamento")
@Getter
@Setter
public class FormaPagamentoDTO extends RepresentationModel<FormaPagamentoDTO> {

	@ApiModelProperty(example = "1",required = true)
	private Long id;

	@ApiModelProperty(example = "Cartão de Crédito", required = true)
	@NotBlank
	private String descricao;

}
