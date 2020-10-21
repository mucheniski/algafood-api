package com.algaworks.algafood.exception;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * @AllArgsConstructor
 * Cria o construtor com todos os parâmetros pelo lombok
 * */
@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private BindingResult bindingResult;
	
}
