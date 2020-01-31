package com.algaworks.algafood.notification;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.Cliente;

@Component
public class NotificadorEmail implements Notificador {
	
	public NotificadorEmail() {
		System.out.println("NotificadorEmail");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s atrav�s do e-mail %s> %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
	}

}
