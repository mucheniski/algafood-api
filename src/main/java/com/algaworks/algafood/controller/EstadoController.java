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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.EstadoDTO;
import com.algaworks.algafood.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	public List<EstadoDTO> listar() {
		return estadoService.listar();
	}
	
	@GetMapping("/{estadoId}")
	public EstadoDTO buscarPorId(@PathVariable Long estadoId) {
		return estadoService.buscarPorId(estadoId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO salvar(@RequestBody @Valid EstadoDTO estadoDTO) {
		return estadoService.salvar(estadoDTO);
	}
	
	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoDTO estadoDTO) {		
		return estadoService.atualizar(estadoId, estadoDTO);
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		estadoService.remover(estadoId);			
	}
	
}
