package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	private CozinhaService cozinhaService;
	
	@GetMapping
	public List<CozinhaDTO> listar() {
		return cozinhaService.listar();
	}
	
	@GetMapping("/nome")
	public List<CozinhaDTO> listarPorNome(@RequestParam String nome) {	
		return cozinhaService.listarPorNome(nome);
	}
	
	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscarPorId(@PathVariable Long cozinhaId) {
		return cozinhaService.buscarPorId(cozinhaId);
	}
	
	@GetMapping("/primeiro")
	public CozinhaDTO bucarPrimeiro() {
		return cozinhaService.bucarPrimeiro();		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO salvar(@RequestBody @Valid CozinhaDTO cozinhaDTO) {
		return cozinhaService.salvar(cozinhaDTO);
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaDTO cozinhaDTO) {
		return cozinhaService.atualizar(cozinhaId, cozinhaDTO);
	}	

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {		
		cozinhaService.remover(cozinhaId);		
	}
	
}
