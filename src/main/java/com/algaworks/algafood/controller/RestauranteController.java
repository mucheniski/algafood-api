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
import com.algaworks.algafood.dto.RestauranteEntradaDTO;
import com.algaworks.algafood.service.RestauranteService;

// TODO: Revisar se realmente é necessário um DTO de entrada e outro de Retorno.

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService service;
	
	@GetMapping
	public List<RestauranteDTO> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public RestauranteDTO buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);		
	}
	
	@GetMapping("/taxa-frete")
	public List<RestauranteDTO> listarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return service.listarPorTaxaFrete(taxaInicial, taxaFinal);		
	}
	
	@GetMapping("/nome-taxa-frete")
	public List<RestauranteDTO> listarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return service.listarPorNomeTaxaFrete(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/com-frete-gratis")
	public List<RestauranteDTO> comFreteGratis(String nome) {	
		return service.comFreteGratis(nome);
	}
	
	@GetMapping("/nome-cozinha")
	public List<RestauranteDTO> listarPorNomeECozinha(String nome, Long cozinhaId) {
		return service.listarPorNomeECozinha(nome, cozinhaId);
	}
	
	@GetMapping("/primeiro")
	public RestauranteDTO buscarPrimeiro() {
		return service.buscarPrimeiro();
	}	
	
	@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO salvar(@RequestBody @Valid RestauranteEntradaDTO dto) {
		return service.salvar(dto);	
	}
	
	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteEntradaDTO dto) {		
		return service.atualizar(id, dto);		
	}
	
	@PutMapping("/{id}/ativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		service.ativar(id);
	}
	
	@PutMapping("/{id}/desativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativar(@PathVariable Long id) {
		service.desativar(id);
	}
	
}
