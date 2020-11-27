package com.algaworks.algafood.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaIdDTO {
	
	@NotNull
	private Long id;

}
