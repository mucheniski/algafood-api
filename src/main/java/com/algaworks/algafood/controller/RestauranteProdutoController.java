package com.algaworks.algafood.controller;

import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.algaworks.algafood.dto.FotoProdutoDTO;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

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

	/*
		FotoProdutoDTO não é anotado com @RequestBody porque não vem como JSON e sim como parâmetros
		da requisição, no postman é o endpoint /atualizar-foto
	 */
	@PutMapping(value = "{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, FotoProdutoDTO fotoProdutoDTO) {

		var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoDTO.getArquivo().getOriginalFilename();
		var arquivoFoto = FileSystems.getDefault().getPath("C:\\ws-developer\\algafood-api\\img\\catalogo", nomeArquivo);

		System.out.println(fotoProdutoDTO.getDescricao());
		System.out.println(arquivoFoto);

		/*
			contentType é o destino do arquivo
		 */
		System.out.println(fotoProdutoDTO.getArquivo().getContentType());

		try {
			fotoProdutoDTO.getArquivo().transferTo(arquivoFoto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
