package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.documentation.CidadeOpenAPI;
import com.algaworks.algafood.dto.input.CidadeInputDTO;
import com.algaworks.algafood.exception.Problema;
import io.swagger.annotations.*;
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

import com.algaworks.algafood.dto.CidadeDTO;
import com.algaworks.algafood.service.CidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeOpenAPI {

	@Autowired
	private CidadeService service;

	@Override
	@GetMapping
	public List<CidadeDTO> listar() {
		return service.listar();
	}

	@Override
	@GetMapping("/{id}")
	public CidadeDTO buscarPorId(@PathVariable Long id) {
		return service.buscarDtoPorId(id);
	}

	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public CidadeDTO salvar(@RequestBody @Valid CidadeInputDTO dto) {
		return service.salvar(dto);
	}

	@Override
	@PutMapping("/{id}")
	public CidadeDTO atualizar(@PathVariable Long id, @RequestBody CidadeDTO dto) {
		return service.atualizar(id, dto);
	}

	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}

}
