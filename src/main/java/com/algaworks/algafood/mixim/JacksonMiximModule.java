package com.algaworks.algafood.mixim;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.entity.Cidade;
import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.entity.Grupo;
import com.algaworks.algafood.entity.Usuario;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMiximModule extends SimpleModule {
	private static final long serialVersionUID = 1L;

	public JacksonMiximModule() {
		
		/* Esse método faz a ligação entre as classes e seus respectivos mixim's */
		setMixInAnnotation(Cidade.class, CidadeMixim.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixim.class);
		setMixInAnnotation(Grupo.class, GrupoMixim.class);
		setMixInAnnotation(Usuario.class, UsuarioMixim.class);
	}
	
}
