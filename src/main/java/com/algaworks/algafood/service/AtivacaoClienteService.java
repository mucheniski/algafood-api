package com.algaworks.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.Cliente;
import com.algaworks.algafood.events.ClienteAtivadoEvent;

@Component
public class AtivacaoClienteService {
	
	@Autowired
	public ApplicationEventPublisher eventPublisher;

	public void ativar(Cliente cliente) {
		cliente.ativar();		
		
		// Dizer para o container que o cliente está ativo
		eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
		
	}

}
