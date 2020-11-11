package com.algaworks.algafood.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.entity.Restaurante;

/*
 * Classe que converte Entidades para DTO
 * */
@Component
public class RestauranteConversor {
	
	public RestauranteRetornoDTO converterParaDTO(Restaurante restaurante) {
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		RestauranteRetornoDTO restauranteDTO = new RestauranteRetornoDTO();
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setNome(restaurante.getNome());
		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDTO.setCozinhaDTO(cozinhaDTO);
		return restauranteDTO;
	}
	
	public List<RestauranteRetornoDTO> converterListaParaDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
							.map(restaurante -> converterParaDTO(restaurante))
							.collect(Collectors.toList());
	}
	
	public Restaurante converterParaObjeto(RestauranteEntradaDTO restauranteEntradaDTO) {
		// TODO: Refatorar para builder do lombok
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteEntradaDTO.getNome());
		restaurante.setTaxaFrete(restauranteEntradaDTO.getTaxaFrete());
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteEntradaDTO.getCozinha().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;
		
	}

}
