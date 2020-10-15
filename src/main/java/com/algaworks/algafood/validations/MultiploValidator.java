package com.algaworks.algafood.validations;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

	private int numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		/*
		 *  Pego o número que é passado na annotation Multiplo que está sendo passada como chave e 
		 *  Atribuo ao numeroMultiplo local
		 */
		this.numeroMultiplo = constraintAnnotation.numero();
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		Boolean valido = true;
		
		if (value != null) {
			BigDecimal valorDecimal = BigDecimal.valueOf(value.doubleValue());
			BigDecimal multiploDecimal= BigDecimal.valueOf(this.numeroMultiplo);
			BigDecimal resto = valorDecimal.remainder(multiploDecimal);
			valido = BigDecimal.ZERO.compareTo(resto) == 0; // O compareTo retorna zero caso sejam iguais
		}
		
		return valido;
	}

}
