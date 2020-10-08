package com.algaworks.algafood.validations;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		/*
		 * Caso não seja especificado através desse set o MessageSource que é o responsável por resolver as 
		 * mensagens do spring do arquivo messages.properties o que vai ser utilizado é o ValidationMessages.properties
		 * no caso como está setado o que vai ser usado é o messages.properties do spring
		 * */		
		bean.setValidationMessageSource(messageSource);
		return bean;
	}
	
}
