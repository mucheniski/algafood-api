package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.documentation.CozinhaOpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.service.CozinhaService;

//TODO: Revisar se realmente é necessário um DTO de entrada e outro de Retorno.
@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaOpenAPI {
	
	
	@Autowired
	private CozinhaService service;
	
	@Override
	@GetMapping
	public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
		return service.listar(pageable);
	}
	
	@Override
	@GetMapping("/nome")
	public List<CozinhaDTO> listarPorNome(@RequestParam String nome) {	
		return service.listarPorNome(nome);
	}
	
	@Override
	@GetMapping("/{id}")
	public CozinhaDTO buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}
	
	@Override
	@GetMapping("/primeiro")
	public CozinhaDTO bucarPrimeiro() {
		return service.bucarPrimeiro();		
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO salvar(@RequestBody @Valid CozinhaDTO dto) {
		return service.salvar(dto);
	}
	
	@Override
	@PutMapping("/{id}")
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaDTO dto) {
		return service.atualizar(id, dto);
	}	

	@Override
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {		
		service.remover(id);		
	}
	
}
