package com.algaworks.algafood.dto.retorno;

import java.math.BigDecimal;

import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.dto.EnderecoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRetornoDTO {
	
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDTO cozinha;
	private Boolean ativo;
	private EnderecoDTO endereco;
	
}
