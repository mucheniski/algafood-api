package com.algaworks.algafood.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioEntradaSemSenhaDTO {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;

}
