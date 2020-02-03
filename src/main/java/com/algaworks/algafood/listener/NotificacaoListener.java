package com.algaworks.algafood.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.customannotation.NivelUrgencia;
import com.algaworks.algafood.customannotation.TipoNotificador;
import com.algaworks.algafood.events.ClienteAtivadoEvent;
import com.algaworks.algafood.notification.Notificador;

@Component
public class NotificacaoListener {
	
	@TipoNotificador(NivelUrgencia.NORMAL)
	@Autowired
	private Notificador notificador;
	
	@EventListener
	public void clienteAtivadoListener(ClienteAtivadoEvent event) {
		notificador.notificar(event.getCliente(), "Seu cadastro no sistema está ativo!");
	}

}
