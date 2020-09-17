package com.algaworks.algafood.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

/*
 * 8.18. Padronizando o formato de problemas no corpo de respostas com a RFC 7807
 * */

@JsonInclude(Include.NON_NULL) // Inclui apenas o que não é null na representação JSON
@Getter
@Builder
public class Problema {

	private Integer status;
	private String tipo;
	private String titulo;
	private String detalhe;
	private String mensagemParaUsuario;
	private LocalDateTime dataHoraAtual;
	private List<Campo> campos;
	
	@Getter
	@Builder
	public static class Campo {
		private String nome;
		private String mensagemUsuario;
	}
	
}
