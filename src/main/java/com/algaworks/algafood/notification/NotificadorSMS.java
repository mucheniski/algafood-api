package com.algaworks.algafood.notification;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.customannotation.NivelUrgencia;
import com.algaworks.algafood.customannotation.TipoNotificador;
import com.algaworks.algafood.domain.Cliente;

@TipoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorSMS implements Notificador {

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.printf("Notificando %s por SMS atrav�s do telefone %s: %s\n", cliente.getNome(), cliente.getTelefone(), mensagem);
	}

}
