package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.controller.EstadoController;
import com.algaworks.algafood.controller.UsuarioController;
import com.algaworks.algafood.controller.UsuarioGrupoController;
import com.algaworks.algafood.links.LinkManager;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.EstadoDTO;
import com.algaworks.algafood.entity.Estado;

@Component
public class EstadoConversor extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LinkManager linkManager;

	public EstadoConversor() {
		super(EstadoController.class, EstadoDTO.class);
	}

	@Override
	public EstadoDTO toModel(Estado estado) {
		var estadoDTO =  modelMapper.map(estado, EstadoDTO.class);
		estadoDTO = linkManager.linkToEstado(estadoDTO);
		return estadoDTO;
	}
	
	public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> estados) {
		return super.toCollectionModel(estados).add(WebMvcLinkBuilder.linkTo(EstadoController.class).withSelfRel());
	}
	
	public Estado converterParaObjeto(EstadoDTO estadoDTO) {
		return modelMapper.map(estadoDTO, Estado.class);
	}
	
	public void copiarParaObjeto(EstadoDTO estadoDTO, Estado estado) {
		estadoDTO.setId(estado.getId());
		modelMapper.map(estadoDTO, estado);
	}
	
}
