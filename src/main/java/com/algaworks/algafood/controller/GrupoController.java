package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.documentation.GrupoOpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoOpenAPI {
	
	@Autowired
	private GrupoService service;
	
	@Override
	@GetMapping
	public List<GrupoDTO> listar() {
		return service.listar();
	}
	
	@Override
	@GetMapping("/{id}")
	public GrupoDTO buscarPorId(@PathVariable Long id) {
		return service.buscarDtoPorId(id);
	}
	
	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public GrupoDTO salvar(@RequestBody @Valid GrupoDTO dto) {
		return service.salvar(dto);
	}
	
	@Override
	@PutMapping("/{id}")
	public GrupoDTO atualizar(@PathVariable Long id, @RequestBody @Valid GrupoDTO dto) {
		return service.atualizar(id, dto);
	}
	
	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}

}
