package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.controller.*;
import com.algaworks.algafood.links.LinkManager;
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

	@Autowired
	private LinkManager linkManager;

	public UsuarioConversor() {
		super(UsuarioController.class, UsuarioDTO.class);
	}

	@Override
	public UsuarioDTO toModel(Usuario usuario) {
		var usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
		usuarioDTO = linkManager.linkToUsuario(usuarioDTO);
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
