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

import com.algaworks.algafood.dto.CozinhaEntradaDTO;
import com.algaworks.algafood.dto.CozinhaRetornoDTO;
import com.algaworks.algafood.service.CozinhaService;

//TODO: Revisar se realmente é necessário um DTO de entrada e outro de Retorno.
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@GetMapping
	public List<CozinhaRetornoDTO> listar() {
		return cozinhaService.listar();
	}
	
	@GetMapping("/nome")
	public List<CozinhaRetornoDTO> listarPorNome(@RequestParam String nome) {	
		return cozinhaService.listarPorNome(nome);
	}
	
	@GetMapping("/{cozinhaId}")
	public CozinhaRetornoDTO buscarPorId(@PathVariable Long cozinhaId) {
		return cozinhaService.buscarPorId(cozinhaId);
	}
	
	@GetMapping("/primeiro")
	public CozinhaRetornoDTO bucarPrimeiro() {
		return cozinhaService.bucarPrimeiro();		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaRetornoDTO salvar(@RequestBody @Valid CozinhaEntradaDTO cozinhaEntradaDTO) {
		return cozinhaService.salvar(cozinhaEntradaDTO);
	}
	
	@PutMapping("/{cozinhaId}")
	public CozinhaRetornoDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaEntradaDTO cozinhaEntradaDTO) {
		return cozinhaService.atualizar(cozinhaId, cozinhaEntradaDTO);
	}	

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {		
		cozinhaService.remover(cozinhaId);		
	}
	
}
