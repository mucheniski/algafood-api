package com.algaworks.algafood.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.algaworks.algafood.dto.RestauranteEntradaDTO;
import com.algaworks.algafood.dto.RestauranteRetornoDTO;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.CozinhaNaoEncotradaException;
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
	public List<RestauranteRetornoDTO> listar() {
		return restauranteService.converterListaParaDTO(restauranteRepository.findAllCustom());
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteRetornoDTO buscarPorId(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarPorId(restauranteId);		
		return restauranteService.converterParaDTO(restaurante);
	}
	
	@GetMapping("/taxa-frete")
	public List<RestauranteRetornoDTO> listarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteService.converterListaParaDTO(restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
	}
	
	@GetMapping("/nome-taxa-frete")
	public List<RestauranteRetornoDTO> listarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteService.converterListaParaDTO(restauranteRepository.findByNomeTaxaFrete(nome, taxaInicial, taxaFinal));
	}
	
	@GetMapping("/com-frete-gratis")
	public List<RestauranteRetornoDTO> comFreteGratis(String nome) {	
		return restauranteService.converterListaParaDTO(restauranteRepository.findComFreteGratis(nome));
	}
	
	@GetMapping("/nome-cozinha")
	public List<RestauranteRetornoDTO> listarPorNomeECozinha(String nome, Long cozinhaId) {
		return restauranteService.converterListaParaDTO(restauranteRepository.consultarPorNome(nome, cozinhaId));
	}
	
	@GetMapping("/primeiro")
	public RestauranteRetornoDTO primeiroRestaurante() {
		return restauranteService.converterParaDTO(restauranteRepository.buscarPrimeiro().get());
	}	
	
	@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteRetornoDTO salvar(@RequestBody @Valid RestauranteEntradaDTO restauranteEntradaDTO) {
		try {
			Restaurante restaurante = restauranteService.converterParaObjeto(restauranteEntradaDTO);
			return restauranteService.converterParaDTO(restauranteService.salvar(restaurante));
		} catch (CozinhaNaoEncotradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteRetornoDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteEntradaDTO restauranteEntradaDTO) {
		
		Restaurante restauranteRecebido = restauranteService.converterParaObjeto(restauranteEntradaDTO);
		Restaurante restauranteAtual = restauranteService.buscarPorId(restauranteId);
		
		BeanUtils.copyProperties(restauranteRecebido, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro"); // Do terceiro parâmetro em diante passamos o que queremos que seja ignorado
		
		try {
			return restauranteService.converterParaDTO(restauranteService.salvar(restauranteAtual));
			
		} catch (EntidadeNaoEncotradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
}
