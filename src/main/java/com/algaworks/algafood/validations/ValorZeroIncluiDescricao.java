package com.algaworks.algafood.validations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * TYPE indica que só pode ser usada em um tipo, ex: Classe, Inferface, etc..
 * Se tentar colocar em um atributo da classe vai dar erro.
 * */
@Target({ ElementType.TYPE }) 
@Retention(RUNTIME)
@Constraint(validatedBy = { ValorZeroIncluiDescricaoValidator.class })
public @interface ValorZeroIncluiDescricao {
	
//	Uma anotação do Bean validation precisa ter essas 3 propriedades
	String message() default "descrição obrigatória inválida";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String atributo();
	String descricao();
	String descricaoObrigatoria();

}
