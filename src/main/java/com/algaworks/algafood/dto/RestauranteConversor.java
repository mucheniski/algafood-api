package com.algaworks.algafood.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.entity.Restaurante;

/*
 * Classe que converte Entidades para DTO
 * */
@Component
public class RestauranteConversor {
	
	@Autowired
	private ModelMapper modelMapper;
	
	/*
	 * Recebe uma entidade e instancia um DTO à partir da entidade 
	 * Atribui corretamente as propriedades de restaurante para o DTO.
	 */
	public RestauranteRetornoDTO converterParaDTO(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteRetornoDTO.class);
	}
	
	public List<RestauranteRetornoDTO> converterListaParaDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
								.map(restaurante -> converterParaDTO(restaurante))
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
	public void copiarParaObjeto(RestauranteEntradaDTO restauranteEntradaDTO, Restaurante restaurante) {
		
		/*
		 * Nesse caso a conzinha precisa ser instanciada para evitar que o hibernate tente substituir o id da cozinha
		 * quando atualizarmos a referência da cozinha de um restaurante, sem instanciar o hibernate pensa que estamos
		 * tentando atualizar o id da entidade cozinha e não apenas a referência dela no restaurante.
		 * ex: mudando de id cozinha 1 para 2 quando atualizamos um restaurante
		 * Resolved [org.springframework.orm.jpa.JpaSystemException: 
		 * identifier of an instance of com.algaworks.algafood.entity.Cozinha was altered from 1 to 2; 
		 * nested exception is org.hibernate.HibernateException: 
		 * identifier of an instance of com.algaworks.algafood.entity.Cozinha was altered from 1 to 2]
		 */
		restaurante.setCozinha(new Cozinha());
		modelMapper.map(restauranteEntradaDTO, restaurante);
	}

}
