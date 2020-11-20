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
	
	@Autowired
	private CozinhaConversor cozinhaConversor;
	
	/*
	 * Recebe uma entidade e instancia um DTO à partir da entidade 
	 * Atribui corretamente as propriedades de restaurante para o DTO.
	 */
	public RestauranteDTO converterParaDTO(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}
	
	public List<RestauranteDTO> converterListaParaDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
								.map(restaurante -> converterParaDTO(restaurante))
								.collect(Collectors.toList());
	}
	
	/*
	 * Recebe um DTO e instancia uma entidade à partir desse DTO.
	 */
	public Restaurante converterParaObjeto(RestauranteDTO restauranteEntradaDTO) {
		return modelMapper.map(restauranteEntradaDTO, Restaurante.class);		
	}
	
	/*
	 * Copia as propriadades do DTO para a Entidade, é mais indicado do que o BeanUtils.copyProperties
	 * porque no caso não precisamos ficar passando mais as propriedades que precisam ser ignoradas para
	 * não retornarem como null, o ModelMapper já tem a inteligência para validar isso.
	 */
	public void copiarParaObjeto(RestauranteDTO restauranteDTO, Restaurante restaurante) {
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setCozinha(cozinhaConversor.converterParaDTO(restaurante.getCozinha()));
		modelMapper.map(restauranteDTO, restaurante);
	}

}
