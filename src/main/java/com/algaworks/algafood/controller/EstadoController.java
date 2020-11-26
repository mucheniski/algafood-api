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
	private EstadoService service;
	
	@GetMapping
	public List<EstadoDTO> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public EstadoDTO buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO salvar(@RequestBody @Valid EstadoDTO dto) {
		return service.salvar(dto);
	}
	
	@PutMapping("/{id}")
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoDTO dto) {		
		return service.atualizar(id, dto);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		service.remover(id);			
	}
	
}
