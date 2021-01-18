package com.algaworks.algafood.filtro;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFiltro {

	private Long usuarioClienteId;
	private Long restauranteId;
	
	/*
	 * A anotação @DateTimeFormat força a implementação desse formato, na requisição REST as datas vem como String
	 * por isso é necessário incluir essa anotação.
	 */
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;
	
}
