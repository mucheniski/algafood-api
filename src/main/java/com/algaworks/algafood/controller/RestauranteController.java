package com.algaworks.algafood.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.dto.RestauranteDTO;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.EntidadeNaoEncotradaException;
import com.algaworks.algafood.exception.NegocioException;
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
	public List<RestauranteDTO> listar() {
		return converterListaParaDTO(restauranteRepository.findAllCustom());
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscarPorId(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarPorId(restauranteId);		
		return converterParaDTO(restaurante);
	}
	
	@GetMapping("/taxa-frete")
	public List<RestauranteDTO> listarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return converterListaParaDTO(restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
	}
	
	@GetMapping("/nome-taxa-frete")
	public List<RestauranteDTO> listarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return converterListaParaDTO(restauranteRepository.findByNomeTaxaFrete(nome, taxaInicial, taxaFinal));
	}
	
	@GetMapping("/com-frete-gratis")
	public List<RestauranteDTO> comFreteGratis(String nome) {	
		return converterListaParaDTO(restauranteRepository.findComFreteGratis(nome));
	}
	
	@GetMapping("/nome-cozinha")
	public List<RestauranteDTO> listarPorNomeECozinha(String nome, Long cozinhaId) {
		return converterListaParaDTO(restauranteRepository.consultarPorNome(nome, cozinhaId));
	}
	
	@GetMapping("/primeiro")
	public RestauranteDTO primeiroRestaurante() {
		return converterParaDTO(restauranteRepository.buscarPrimeiro().get());
	}	
	
	@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO salvar(@RequestBody @Valid Restaurante restaurante) {
		return converterParaDTO(restauranteService.salvar(restaurante));
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restauranteRecebida) {
		Restaurante restauranteAtual = restauranteService.buscarPorId(restauranteId);
		BeanUtils.copyProperties(restauranteRecebida, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro"); // Do terceiro parâmetro em diante passamos o que queremos que seja ignorado
		
		try {
			return converterParaDTO(restauranteService.salvar(restauranteAtual));
			
		} catch (EntidadeNaoEncotradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}	
	
	@PatchMapping("/{restauranteId}")
	public RestauranteDTO atualizarParcial(@PathVariable Long restauranteId, @RequestBody @Valid Map<String, Object> camposAtualizados, HttpServletRequest request) {		
		Restaurante restauranteAtual = restauranteService.buscarPorId(restauranteId); 
		restauranteService.mergeCampos(camposAtualizados, restauranteAtual, request);			
		restauranteService.valida(restauranteAtual, "restaurante");
		
		try {
			return atualizar(restauranteId, restauranteAtual);
			
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	private RestauranteDTO converterParaDTO(Restaurante restaurante) {
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		RestauranteDTO restauranteDTO = new RestauranteDTO();
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setNome(restaurante.getNome());
		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDTO.setCozinhaDTO(cozinhaDTO);
		return restauranteDTO;
	}
	
	private List<RestauranteDTO> converterListaParaDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
							.map(restaurante -> converterParaDTO(restaurante))
							.collect(Collectors.toList());
	}
	
}
