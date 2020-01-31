package com.algaworks.algafood.service;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.Cliente;
import com.algaworks.algafood.notification.Notificador;

@Component
public class AtivacaoClienteService {
	
	private Notificador notificador;
	
	public AtivacaoClienteService(Notificador notificador) {		
		this.notificador = notificador;
		
		System.out.println("AtivacaoClienteService: " + notificador);
	}

	public void ativar(Cliente cliente) {
		cliente.ativar();		
		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}

}
