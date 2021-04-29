package com.algaworks.algafood.dto;

import java.math.BigDecimal;

import com.algaworks.algafood.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class RestauranteRetornoDTO extends RepresentationModel<RestauranteRetornoDTO> {
	
	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNomes.class })
	private Long id;
	
	@JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNomes.class })
	private String nome;
	
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;
	
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoRetornoDTO endereco;
	
}
