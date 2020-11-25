package com.algaworks.algafood.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoDTO {
	
	private Long id;
	
	@NotBlank
	private String descricao;

}
