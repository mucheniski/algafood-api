package com.algaworks.algafood.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileContentTypeValidator.class })
public @interface FileContentType {
	
//	Uma anotação do Bean validation precisa ter essas 3 propriedades
	String message() default "Tipo do arquivo inválido";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	// Propriedades personalizadas
	String[] allowedTypes();

}
