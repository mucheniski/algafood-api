package com.algaworks.algafood.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.RestauranteDTO;
import com.algaworks.algafood.service.RestauranteService;

// TODO: Revisar se realmente é necessário um DTO de entrada e outro de Retorno.

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;
	
	@GetMapping
	public List<RestauranteDTO> listar() {
		return restauranteService.listar();
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscarPorId(@PathVariable Long restauranteId) {
		return restauranteService.buscarPorId(restauranteId);		
	}
	
	@GetMapping("/taxa-frete")
	public List<RestauranteDTO> listarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteService.listarPorTaxaFrete(taxaInicial, taxaFinal);		
	}
	
	@GetMapping("/nome-taxa-frete")
	public List<RestauranteDTO> listarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteService.listarPorNomeTaxaFrete(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/com-frete-gratis")
	public List<RestauranteDTO> comFreteGratis(String nome) {	
		return restauranteService.comFreteGratis(nome);
	}
	
	@GetMapping("/nome-cozinha")
	public List<RestauranteDTO> listarPorNomeECozinha(String nome, Long cozinhaId) {
		return restauranteService.listarPorNomeECozinha(nome, cozinhaId);
	}
	
	@GetMapping("/primeiro")
	public RestauranteDTO buscarPrimeiro() {
		return restauranteService.buscarPrimeiro();
	}	
	
	@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO salvar(@RequestBody @Valid RestauranteDTO restauranteDTO) {
		return restauranteService.salvar(restauranteDTO);	
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteDTO restauranteDTO) {		
		return restauranteService.atualizar(restauranteId, restauranteDTO);		
	}
	
	@PutMapping("/{restauranteId}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		restauranteService.ativar(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/desativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativar(@PathVariable Long restauranteId) {
		restauranteService.desativar(restauranteId);
	}
	
}
