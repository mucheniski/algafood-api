package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.repository.RestauranteRepository;

public class RestauranteMain {

	public static void main(String[] args) {		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
							.web(WebApplicationType.NONE)
							.run(args);
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		
		restauranteRepository.listar().forEach(System.out::println);
		
//		criar(restauranteRepository);
//		
//		buscar(restauranteRepository, 1L);
//		
//		atualizar(restauranteRepository, 1L);
//		
//		remover(restauranteRepository, 1L);
		
	}

//	private static void remover(RestauranteRepository restauranteRepository, long id) {
//		Restaurante restaurante = new Restaurante();
//		restaurante.setId(id);
//		restauranteRepository.remover(restaurante);		
//	}
//
//	private static void atualizar(RestauranteRepository restauranteRepository, long id) {
//		Restaurante restaurante = new Restaurante();
//		restaurante.setId(id);
//		restaurante.setNome("Atualizada");
//		restauranteRepository.salvar(restaurante);
//	}
//
//	private static Restaurante buscar(RestauranteRepository restauranteRepository, long id) {
//		Restaurante restaurante = restauranteRepository.buscar(id);	
//		System.out.println("Busca por id: " + restaurante.getNome());
//		return restaurante;
//	}
//
//	private static void criar(RestauranteRepository restauranteRepository) {
//		Restaurante restaurante1 = new Restaurante("Brasileira");
//		Restaurante restaurante2 = new Restaurante("Japonesa");
//		restaurante1 = restauranteRepository.salvar(restaurante1);
//		restaurante2 = restauranteRepository.salvar(restaurante2);
//		
//		System.out.printf("%d - %s\n", restaurante1.getId(), restaurante1.getNome());
//		System.out.printf("%d - %s\n", restaurante2.getId(), restaurante2.getNome());
//	}
	
}
