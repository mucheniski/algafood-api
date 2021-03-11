package com.algaworks.algafood.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.RestauranteEntradaDTO;
import com.algaworks.algafood.dto.RestauranteRetornoDTO;
import com.algaworks.algafood.service.RestauranteService;
import com.algaworks.algafood.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService service;	
	
	@GetMapping
	public ResponseEntity<List<RestauranteRetornoDTO>> listar() {

		List<RestauranteRetornoDTO> list = service.listar();

		return ResponseEntity.ok()
					.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
					.body(list);
	}
	
	/*
	 * MappingJacksonValue é um wrapper que serve para envelopar o retorno, permite atribuir dinamicamente uma JsonView.
	 * 
	 * O required do @RequestParam por dafault é true, para que não seja pedido no request caso não necessário é preciso 
	 * colocar o false.
	 */
	@GetMapping("/listar-envelopado")
	public MappingJacksonValue listarEnvelopado(@RequestParam(required = false) String tipoRetorno) {
		return service.listarEnvelopado(tipoRetorno);
	}
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping(params = "tipoRetorno=resumo")
	public List<RestauranteRetornoDTO> listarResumido() {
		return service.listar();
	}
	
	@JsonView(RestauranteView.ApenasNomes.class)
	@GetMapping(params = "tipoRetorno=apenas-nomes")
	public List<RestauranteRetornoDTO> listarApenasNomes() {
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
	
	@PutMapping("/ativar-todos")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarTodos(@RequestBody List<Long> ids) {
		service.ativarTodos(ids);
	}
	
	@PutMapping("/{id}/desativar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativar(@PathVariable Long id) {
		service.desativar(id);
	}
	
	@PutMapping("/desativar-todos")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativarTodos(@RequestBody List<Long> ids) {
		service.desativarTodos(ids);
	}
	
	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		service.abrir(id);
	}
		
	@PutMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		service.fechar(id);
	}
	
}
