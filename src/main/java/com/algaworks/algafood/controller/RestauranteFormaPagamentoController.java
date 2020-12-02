package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.FormaPagamentoDTO;
import com.algaworks.algafood.dto.conversor.FormaPagamentoConversor;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagamentoConversor formaPagamentoConvesor;
	
	@GetMapping
	public List<FormaPagamentoDTO> listarFormasPagamento(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
		return formaPagamentoConvesor.converterListaParaDTO(restaurante.getFormasPagamento());
	}
	
	
	/*
	 * O restauranteId é um bind com o parametro passado no inicio da URI /restaurantes/{restauranteId}/formas-pagamento
	 * O formaPagamentoId é um bind com o passado no método.
	 */
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desvincularFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desvincularFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	/*
	 * O restauranteId é um bind com o parametro passado no inicio da URI /restaurantes/{restauranteId}/formas-pagamento
	 * O formaPagamentoId é um bind com o passado no método.
	 */
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void vincularFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.vincularFormaPagamento(restauranteId, formaPagamentoId);
	}

}
