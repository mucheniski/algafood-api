package com.algaworks.algafood.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.dto.FotoProdutoDTO;
import com.algaworks.algafood.dto.FotoProdutoPutDTO;
import com.algaworks.algafood.service.ProdutoService;
import com.algaworks.algafood.service.RestauranteProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private RestauranteProdutoService restauranteProdutoService;

	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public List<ProdutoDTO> listaProdutos(@PathVariable Long restauranteId) {
		return restauranteProdutoService.listarProdutos(restauranteId);
	}
	
	@GetMapping("/inativos")
	public List<ProdutoDTO> listaProdutosOpcaoInativos(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
		return restauranteProdutoService.listarProdutosOpcaoInativo(restauranteId, incluirInativos);
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscarProdutoPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return restauranteProdutoService.buscarProdutoDTOPorRestaurante(restauranteId, produtoId);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ProdutoDTO adicionarProduto(@RequestBody @Valid ProdutoDTO produtoDTO, @PathVariable Long restauranteId) {
		return restauranteProdutoService.adicionarProduto(produtoDTO, restauranteId);
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoDTO produtoDTO) {
		return restauranteProdutoService.atualizarProduto(restauranteId, produtoId, produtoDTO);
	}

	/*
		FotoProdutoDTO não é anotado com @RequestBody porque não vem como JSON e sim como parâmetros
		da requisição, no postman é o endpoint /atualizar-foto
	 */
	@PutMapping(value = "/{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO salvarFotoProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoPutDTO fotoProdutoPutDTO) throws IOException {
		return restauranteProdutoService.salvarFotoProduto(restauranteId, produtoId, fotoProdutoPutDTO);
	}

	@GetMapping("/{produtoId}/foto-json")
	public FotoProdutoDTO buscarDadosFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return restauranteProdutoService.buscarFotoProdutoPorRestaurante(restauranteId, produtoId);
	}

	@GetMapping("/{produtoId}/foto-imagem")
	public ResponseEntity<InputStreamResource> mostrarImagemFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		InputStream fotoInputStream = restauranteProdutoService.buscarImagemFotoProdutoPorRestaurante(restauranteId, produtoId);

		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(new InputStreamResource(fotoInputStream));
	}

}
