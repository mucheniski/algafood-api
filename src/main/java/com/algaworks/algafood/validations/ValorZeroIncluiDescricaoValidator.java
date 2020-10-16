package com.algaworks.algafood.validations;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

	private String atributo;
	private String descricao;
	private String descricaoObrigatoria;
	
	@Override
	public void initialize(ValorZeroIncluiDescricao valorZeroIncluiDescricao) {
		this.atributo = valorZeroIncluiDescricao.atributo();
		this.descricao = valorZeroIncluiDescricao.descricao();
		this.descricaoObrigatoria = valorZeroIncluiDescricao.descricaoObrigatoria();
	}
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		
		Boolean valido = true;
		
		try {
			
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), atributo)
											.getReadMethod()
											.invoke(objetoValidacao);
			
			String nome = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricao)
											.getReadMethod()
											.invoke(objetoValidacao);
			
			if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && nome != null) {
				valido = nome.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
			}
			
			return valido;
			
		} catch (Exception e) {
			throw new ValidationException(e);
		}
		
	}

}
