package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	
	@Autowired
	private CozinhaService service;
	
	@GetMapping
	public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
		return service.listar(pageable);
	}
	
	@GetMapping("/nome")
	public List<CozinhaDTO> listarPorNome(@RequestParam String nome) {	
		return service.listarPorNome(nome);
	}
	
	@GetMapping("/{id}")
	public CozinhaDTO buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}
	
	@GetMapping("/primeiro")
	public CozinhaDTO bucarPrimeiro() {
		return service.bucarPrimeiro();		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO salvar(@RequestBody @Valid CozinhaDTO dto) {
		return service.salvar(dto);
	}
	
	@PutMapping("/{id}")
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaDTO dto) {
		return service.atualizar(id, dto);
	}	

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {		
		service.remover(id);		
	}
	
}
