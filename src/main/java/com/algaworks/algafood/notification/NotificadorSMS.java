package com.algaworks.algafood.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.Cliente;

@Qualifier("segundo")
@Component
public class NotificadorSMS implements Notificador {

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s por SMS atrav�s do telefone %s: %s\n", cliente.getNome(), cliente.getTelefone(), mensagem);
	}

}
