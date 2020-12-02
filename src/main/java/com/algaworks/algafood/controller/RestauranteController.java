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

import com.algaworks.algafood.dto.entrada.RestauranteEntradaDTO;
import com.algaworks.algafood.dto.retorno.RestauranteRetornoDTO;
import com.algaworks.algafood.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService service;
	
	@GetMapping
	public List<RestauranteRetornoDTO> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public RestauranteRetornoDTO buscarPorId(@PathVariable Long id) {
		return service.buscarDtoPorId(id);		
	}
	
	@GetMapping("/taxa-frete")
	public List<RestauranteRetornoDTO> listarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return service.listarPorTaxaFrete(taxaInicial, taxaFinal);		
	}
	
	@GetMapping("/nome-taxa-frete")
	public List<RestauranteRetornoDTO> listarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return service.listarPorNomeTaxaFrete(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/com-frete-gratis")
	public List<RestauranteRetornoDTO> comFreteGratis(String nome) {	
		return service.comFreteGratis(nome);
	}
	
	@GetMapping("/nome-cozinha")
	public List<RestauranteRetornoDTO> listarPorNomeECozinha(String nome, Long cozinhaId) {
		return service.listarPorNomeECozinha(nome, cozinhaId);
	}
	
	@GetMapping("/primeiro")
	public RestauranteRetornoDTO buscarPrimeiro() {
		return service.buscarPrimeiro();
	}	
	
	@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteRetornoDTO salvar(@RequestBody @Valid RestauranteEntradaDTO dto) {
		return service.salvar(dto);	
	}
	
	@PutMapping("/{id}")
	public RestauranteRetornoDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteEntradaDTO dto) {		
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
