package com.algaworks.algafood.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.algaworks.algafood.documentation.CidadeOpenAPI;
import com.algaworks.algafood.dto.input.CidadeInputDTO;
import com.algaworks.algafood.entity.Cidade;
import com.algaworks.algafood.exception.Problema;
import com.algaworks.algafood.util.URIGenerator;
import io.swagger.annotations.*;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

		CidadeDTO cidadeDTO = service.buscarDtoPorId(id);
		// Criando um link dinamicamente para o CidadeController/cidadeDTO.id ex .../cidades/1
		cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class).slash(cidadeDTO.getId()).withSelfRel());

		cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withRel("cidades"));
		cidadeDTO.getEstado().add(WebMvcLinkBuilder.linkTo(EstadoController.class).slash(cidadeDTO.getEstado().getId()).withSelfRel());

		return cidadeDTO;
	}

	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public CidadeDTO salvar(@RequestBody @Valid CidadeInputDTO dto) {

		CidadeDTO cidadeDTO = service.salvar(dto);

		URIGenerator.addUriInHeaderLocation(cidadeDTO.getId());

		return cidadeDTO;
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
