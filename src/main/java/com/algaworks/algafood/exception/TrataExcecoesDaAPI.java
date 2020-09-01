package com.algaworks.algafood.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.enuns.TipoProblema;


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
	    HttpStatus status = HttpStatus.NOT_FOUND;	    
	    
	    Problema problema = criarUmProblema(status, TipoProblema.ENTIDADE_NAO_ENCONTRADA, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
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
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object object, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		if (object == null) {
			
			object = Problema.builder()
					.titulo(status.getReasonPhrase())
					.status(status.value())
					.build();
			
		} else if (object instanceof String) {
			
			object = Problema.builder()
					.titulo((String) object )
					.status(status.value())
					.build();
			
		}
		
		return super.handleExceptionInternal(ex, object, headers, status, request);
	}
	
	/*
	 * O builder é criado automáticamente pelo Lombok dentro da classe marcada com @Builder
	 * */
	private Problema.ProblemaBuilder criarUmProblema(HttpStatus status, TipoProblema tipoProblema, String detalhe) {
		
		return Problema.builder()
					   .status(status.value())
					   .tipo(tipoProblema.getUri())
					   .titulo(tipoProblema.getTitulo())
					   .detalhe(detalhe);
	}

}
