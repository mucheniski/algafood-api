package com.algaworks.algafood.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/*
 * ResponseEntityExceptionHandler já é a classe padrão do Spring para tratativa de exceptions do Spring
 * 
 * Também Dentro deste componente podem ser adicionados ExceptionHandler para que todos os erros de controllers 
 * sejam tratados aqui
 * */

@ControllerAdvice
public class TrataExcecoesDaAPI extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EntidadeNaoEncotradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncotradaException( EntidadeNaoEncotradaException ex, WebRequest request ) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException( EntidadeEmUsoException ex, WebRequest request ) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);		
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException( NegocioException ex, WebRequest request ) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);		
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		if (body == null) {
			
			body = Problema.builder()
					.dataHora(LocalDateTime.now())
					.mensagem(status.getReasonPhrase())
					.build();
			
		} else if (body instanceof String) {
			
			body = Problema.builder()
					.dataHora(LocalDateTime.now())
					.mensagem((String) body )
					.build();
			
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

}
