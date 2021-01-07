package com.algaworks.algafood.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.ProdutoDTO;
import com.algaworks.algafood.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@GetMapping
	public List<ProdutoDTO> listaProdutos(@PathVariable Long restauranteId) {
		return restauranteService.listarProdutos(restauranteId);
	}
	
	@GetMapping("/inativos")
	public List<ProdutoDTO> listaProdutosOpcaoInativos(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
		return restauranteService.listarProdutosOpcaoInativo(restauranteId, incluirInativos);
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscarProdutoPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return restauranteService.buscarProdutoPorRestaurante(restauranteId, produtoId);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ProdutoDTO adicionarProduto(@RequestBody @Valid ProdutoDTO produtoDTO, @PathVariable Long restauranteId) {
		return restauranteService.adicionarProduto(produtoDTO, restauranteId);		
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoDTO produtoDTO) {
		return restauranteService.atualizarProduto(restauranteId, produtoId, produtoDTO);
	}

}
