package com.algaworks.algafood.notification;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/* Ao criar a classe com a anotação em @configurationProperties igual às propriedades do application.properties
 * automáticamente o spring já associa com as variáveis hostServidor = host-servidor e portaServidor = porta-servidor
 * que estão no application.properties
 * notificador.email.host-servidor=smtp.algafood.com.br
 * notificador.email.porta-servidor=25
 * Pois estão agrupadas no caminho notificador.email
 * */

@Component
@ConfigurationProperties("notificador.email")
public class NotificadorProperties {
	
	/*
	 * Host do servidor de e-mail 
	 */
	private String hostServidor;
	
	/*
	 * Porta do servidor de e-mail
	 */	
	private Integer portaServidor;
	
	public String getHostServidor() {
		return hostServidor;
	}
	
	public void setHostServidor(String hostServidor) {
		this.hostServidor = hostServidor;
	}
	
	public Integer getPortaServidor() {
		return portaServidor;
	} 
	
	public void setPortaServidor(Integer portaServidor) {
		this.portaServidor = portaServidor;
	}

}
