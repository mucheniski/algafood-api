package com.algaworks.algafood.service;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.Cliente;
import com.algaworks.algafood.notification.NotificadorEmail;

@Component
public class AtivacaoClienteService {
	
	private NotificadorEmail notificador;
	
	public void ativar(Cliente cliente) {
		cliente.ativar();		
		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}

}
