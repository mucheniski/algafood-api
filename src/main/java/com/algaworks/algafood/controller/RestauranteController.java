package com.algaworks.algafood.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.repository.RestauranteRepository;
import com.algaworks.algafood.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAllCustom();
	}
	
	@GetMapping("/taxa-frete")
	public List<Restaurante> listarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/nome-taxa-frete")
	public List<Restaurante> listarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByNomeTaxaFrete(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/com-frete-gratis")
	public List<Restaurante> comFreteGratis(String nome) {	
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	@GetMapping("/nome-cozinha")
	public List<Restaurante> listarPorNomeECozinha(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscarPorId(@PathVariable Long restauranteId) {
		return restauranteService.buscarPorId(restauranteId);
	}
	
	@GetMapping("/primeiro")
	public Optional<Restaurante> primeiroRestaurante() {
		return restauranteRepository.buscarPrimeiro();
	}
	
	@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante salvar(@RequestBody Restaurante restaurante) {
		return restauranteService.salvar(restaurante);
	}
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restauranteRecebida) {
		Restaurante restauranteAtual = restauranteService.buscarPorId(restauranteId);
		BeanUtils.copyProperties(restauranteRecebida, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro"); // Do terceiro parâmetro em diante passamos o que queremos que seja ignorado
		return restauranteService.salvar(restauranteAtual);
	}	
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> camposAtualizados) {		
		Restaurante restauranteAtual = restauranteService.buscarPorId(restauranteId); 
		restauranteService.mergeCampos(camposAtualizados, restauranteAtual);		
		return atualizar(restauranteId, restauranteAtual);
	}	
	
}
