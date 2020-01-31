package com.algaworks.algafood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.notification.NotificadorEmail;

@Configuration
public class NotificacaoConfig {
	
	@Bean
	public NotificadorEmail notificadorEmail() {
		NotificadorEmail notificadorEmail = new NotificadorEmail("smpt.algamail.com.br");
		notificadorEmail.setCaixaAlta(true);
		return notificadorEmail;
	}

}
