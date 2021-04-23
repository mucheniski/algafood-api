package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.controller.*;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.UsuarioEntradaDTO;
import com.algaworks.algafood.dto.UsuarioEntradaSemSenhaDTO;
import com.algaworks.algafood.dto.UsuarioDTO;
import com.algaworks.algafood.entity.Usuario;

@Component
public class UsuarioConversor extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {
	
	@Autowired
	private ModelMapper modelMapper;

	public UsuarioConversor() {
		super(UsuarioController.class, UsuarioDTO.class);
	}

	@Override
	public UsuarioDTO toModel(Usuario usuario) {
		var usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);

		Link linkBuscarPorId = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarPorId(usuarioDTO.getId())).withSelfRel();
		Link linkListar = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel("usuarios");
		Link linkGrupos = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class).listarGruposPorUsuario(usuarioDTO.getId())).withSelfRel();

		usuarioDTO.add(linkBuscarPorId);
		usuarioDTO.add(linkListar);
		usuarioDTO.add(linkGrupos);

		return usuarioDTO;
	}

	@Override
	public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> usuarios) {
		return super.toCollectionModel(usuarios).add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
	}
	
	public Usuario converterParaObjeto(UsuarioEntradaDTO dto) {
		return modelMapper.map(dto, Usuario.class);
	}
	
	public void copiarParaObjeto(UsuarioEntradaSemSenhaDTO dto, Usuario	usuario) {
		modelMapper.map(dto, usuario);
	}


}
