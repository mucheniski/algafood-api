package com.algaworks.algafood.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoEntradaDTO {
	
	@NotBlank
	private String nome;

}
