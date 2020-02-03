package com.algaworks.algafood.customannotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.annotation.Qualifier;

@Retention(RetentionPolicy.RUNTIME) // Annotation pode ser lida em tempo de execução
@Qualifier
public @interface TipoNotificador {
	
	NivelUrgencia value(); // O value aqui suprime a necessidade de value na anotação.

}
