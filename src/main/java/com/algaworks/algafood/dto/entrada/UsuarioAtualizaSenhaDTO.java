package com.algaworks.algafood.dto.entrada;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizaSenhaDTO {
	
	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String senhaNova;

}
