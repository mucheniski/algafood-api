package com.algaworks.algafood.events;

import com.algaworks.algafood.domain.Cliente;

public class ClienteAtivadoEvent {
	
	private Cliente cliente;

	public ClienteAtivadoEvent(Cliente cliente) {		
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

}
