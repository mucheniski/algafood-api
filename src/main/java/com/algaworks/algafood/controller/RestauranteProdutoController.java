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
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

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

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{produtoId}/foto")
	public void deletarFotoProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		restauranteProdutoService.deletarFotoProduto(restauranteId, produtoId);
	}

	@GetMapping("/{produtoId}/foto-json")
	public FotoProdutoDTO buscarDadosFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return restauranteProdutoService.buscarFotoProdutoPorRestaurante(restauranteId, produtoId);
	}

	/*
		@RequestHeader serve para que o consumidor da API envie informações no request
		no caso o name accept busca as informações que foram passadas no accept do postman
		que são os tipos de media aceitos na requisição image/png, image/jpeg ou image/* que aceita todos os tipos
		14.16. Corrigindo exception handler de media type não aceita
		Não foi necessário corrigir pois implementei em endpoints separados
	 */
	@GetMapping("/{produtoId}/foto-imagem")
	public ResponseEntity<InputStreamResource> mostrarImagemFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestHeader(name = "accept") String mediasAceitasHeader) throws HttpMediaTypeNotAcceptableException {
		InputStream fotoInputStream = restauranteProdutoService.buscarImagemFotoProdutoPorRestaurante(restauranteId, produtoId, mediasAceitasHeader);

		return ResponseEntity.ok()
				.body(new InputStreamResource(fotoInputStream));
	}

}
