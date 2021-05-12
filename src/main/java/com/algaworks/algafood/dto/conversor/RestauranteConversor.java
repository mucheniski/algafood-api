package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.controller.RestauranteController;
import com.algaworks.algafood.links.LinkManager;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.RestauranteEntradaDTO;
import com.algaworks.algafood.dto.RestauranteRetornoDTO;
import com.algaworks.algafood.entity.Restaurante;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/*
 * Classe que converte Entidades para DTO
 * */
@Component
public class RestauranteConversor extends RepresentationModelAssemblerSupport<Restaurante, RestauranteRetornoDTO> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LinkManager linkManager;

	public RestauranteConversor() {
		super(RestauranteController.class, RestauranteRetornoDTO.class);
	}

	/*
	 * Recebe uma entidade e instancia um DTO à partir da entidade 
	 * Atribui corretamente as propriedades de restaurante para o DTO.
	 */
	@Override
	public RestauranteRetornoDTO toModel(Restaurante restaurante) {
		var restauranteRetornoDTO = modelMapper.map(restaurante, RestauranteRetornoDTO.class);
		restauranteRetornoDTO = linkManager.linkToRestaurante(restauranteRetornoDTO, restaurante);
		return restauranteRetornoDTO;
	}

	public List<RestauranteRetornoDTO> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
								.map(restaurante -> toModel(restaurante))
								.collect(Collectors.toList());
	}
	
	/*
	 * Recebe um DTO e instancia uma entidade à partir desse DTO.
	 */
	public Restaurante converterParaObjeto(RestauranteEntradaDTO restauranteEntradaDTO) {
		return modelMapper.map(restauranteEntradaDTO, Restaurante.class);		
	}
	
	/*
	 * Copia as propriadades do DTO para a Entidade, é mais indicado do que o BeanUtils.copyProperties
	 * porque no caso não precisamos ficar passando mais as propriedades que precisam ser ignoradas para
	 * não retornarem como null, o ModelMapper já tem a inteligência para validar isso.
	 */
	public void copiarParaObjeto(RestauranteEntradaDTO dto, Restaurante restaurante) {
		modelMapper.map(dto, restaurante);
	}

}
