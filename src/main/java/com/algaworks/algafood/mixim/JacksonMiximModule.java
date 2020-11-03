package com.algaworks.algafood.mixim;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.entity.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMiximModule extends SimpleModule {
	private static final long serialVersionUID = 1L;

	public JacksonMiximModule() {
		
		/* Esse método faz a ligação entre as classes e seus respectivos mixim's */
		setMixInAnnotation(Restaurante.class, RestauranteMixim.class);
	}
	
}
