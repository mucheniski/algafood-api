package com.algaworks.algafood.dto.entrada;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeIdDTO {

	@NotNull
	private Long id;
	
}
