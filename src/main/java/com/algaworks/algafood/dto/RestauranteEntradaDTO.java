package com.algaworks.algafood.dto;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteEntradaDTO {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid // Faz a validação em cascata das propriedades da cozinha mapeadas aqui, como no caso o ID
	@NotNull
	private CozinhaIdDTO cozinha;
	
}
