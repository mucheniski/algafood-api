package com.algaworks.algafood.notification;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.customannotation.NivelUrgencia;
import com.algaworks.algafood.customannotation.TipoNotificador;
import com.algaworks.algafood.domain.Cliente;

@Profile("prod")
@TipoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmail implements Notificador {
	
	public NotificadorEmail() {
		System.out.println("Notificador e-mail real");
	}

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s atrav�s do e-mail %s: %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
	}

}
