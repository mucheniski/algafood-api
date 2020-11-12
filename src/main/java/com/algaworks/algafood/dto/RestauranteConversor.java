package com.algaworks.algafood.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.entity.Restaurante;

/*
 * Classe que converte Entidades para DTO
 * */
@Component
public class RestauranteConversor {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteRetornoDTO converterParaDTO(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteRetornoDTO.class); // Atribui corretamente as propriedades de restaurante para o DTO.
	}
	
	public List<RestauranteRetornoDTO> converterListaParaDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
							.map(restaurante -> converterParaDTO(restaurante))
							.collect(Collectors.toList());
	}
	
	public Restaurante converterParaObjeto(RestauranteEntradaDTO restauranteEntradaDTO) {
		return modelMapper.map(restauranteEntradaDTO, Restaurante.class);		
	}

}
