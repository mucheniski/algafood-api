package com.algaworks.algafood.squiggly;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {
	
	/*
	 * Sempre que as requisições http chegarem na API vão passar por este filtro aqui
	 * assim será possível fazer o filtro, antes de retornar a requisição.
	 */
	@Bean
	public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
		
		/*
		 * ObjectMapper é do jackson, assim que subir o sistema o spring já vai injetar um aqui automaticamente
		 * pois o método recebe um ObjectMapper como parâmetro.
		 */
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider());
		
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
		filterRegistration.setFilter(new SquigglyRequestFilter());
		filterRegistration.setOrder(1);
		
//		/*
//		 * caso queira filtrar apenas para urls específicas, podem ser adicionadas com o método setUtlPatterns
//		 * no exemplo abaixo só será permitido o filtro do squiggly nas urls descritas
//		 */
//		List<String> urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");
//		filterRegistration.setUrlPatterns(urlPatterns);
		
		return filterRegistration;
	}

}
