package com.algaworks.algafood.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Dentro deste componentes podem ser adicionados ExceptionHandler para que todos os erros de controllers sejam tratados aqui
public class TrataExcecoesDaAPI {
	
	@ExceptionHandler(EntidadeNaoEncotradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncotradaException( EntidadeNaoEncotradaException e ) {
		
		Problema problema = Problema.builder()
								.dataHora(LocalDateTime.now())
								.mensagem(e.getMessage())
								.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(problema);
		
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException( NegocioException e ) {
		
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(problema);
		
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException() {
		
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem("O tipo de arquivo não é aceito.")
				.build();
		
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.body(problema);
		
	}

}
