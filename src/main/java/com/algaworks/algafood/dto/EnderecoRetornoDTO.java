package com.algaworks.algafood.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRetornoDTO {
	
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String nomeCidade;
	private String nomeEstado;

}
