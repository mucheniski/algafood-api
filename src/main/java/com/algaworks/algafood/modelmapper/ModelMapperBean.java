package com.algaworks.algafood.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Como o ModelMapper não é uma propriedade do Spring, é preciso criar essa classe
 * para que seja feita uma instância de ModelMapper como um bean do Spring assim
 * que o projeto iniciar.
 * */
@Configuration
public class ModelMapperBean {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
