package com.algaworks.algafood.controller;

import javax.validation.Valid;

import com.algaworks.algafood.documentation.CidadeOpenAPI;
import com.algaworks.algafood.dto.input.CidadeInputDTO;
import com.algaworks.algafood.util.URIGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
	public CollectionModel<CidadeDTO> listar() {
		CollectionModel<CidadeDTO> cidadeDTOCollectionModel = service.listar();

		// Adicionando um link para cada cidade
		cidadeDTOCollectionModel.forEach(cidadeDTO -> {

			Link linkBuscarPorId = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscarDtoPorId(cidadeDTO.getId())).withSelfRel();
			Link linkListar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).listar()).withRel("cidades");
			Link linkEstado = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).buscarDtoPorId(cidadeDTO.getEstado().getId())).withSelfRel();

			cidadeDTO.add(linkBuscarPorId);
			cidadeDTO.add(linkListar);
			cidadeDTO.getEstado().add(linkEstado);

		});

		// Adicionar o link para o endpoint de cidades
		cidadeDTOCollectionModel.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());

		return cidadeDTOCollectionModel;
	}

	@Override
	@GetMapping("/{id}")
	public CidadeDTO buscarDtoPorId(@PathVariable Long id) {

		CidadeDTO cidadeDTO = service.buscarDtoPorId(id);

		/*
		* o methodOn cria uma proxy do controller e já mapeia automáticamente o método sendo usado, é útil para que caso seja alterado algum
		* mapeamento no controller o novo link já é alterado automaticamente, não precisamos ficar criando com slash
		* Criando um link dinamicamente para o CidadeController/cidadeDTO.id ex .../cidades/1
		* é possível porque o CidadeDTO está estendendo RepresentationModel
		* */
		Link linkBuscarPorId = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscarDtoPorId(cidadeDTO.getId())).withSelfRel();
		Link linkListar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).listar()).withRel("cidades");
		Link linkEstado = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).buscarDtoPorId(cidadeDTO.getEstado().getId())).withSelfRel();

		cidadeDTO.add(linkBuscarPorId);
		cidadeDTO.add(linkListar);
		cidadeDTO.getEstado().add(linkEstado);

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
