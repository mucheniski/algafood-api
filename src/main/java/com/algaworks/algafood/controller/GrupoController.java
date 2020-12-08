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

import com.algaworks.algafood.dto.GrupoDTO;
import com.algaworks.algafood.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoService service;
	
	@GetMapping
	public List<GrupoDTO> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public GrupoDTO buscarPorId(@PathVariable Long id) {
		return service.buscarDtoPorId(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public GrupoDTO salvar(@RequestBody @Valid GrupoDTO dto) {
		return service.salvar(dto);
	}
	
	@PutMapping("/{id}")
	public GrupoDTO atualizar(@PathVariable Long id, @RequestBody @Valid GrupoDTO dto) {
		return service.atualizar(id, dto);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}

}
