package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.entrada.UsuarioEntradaDTO;
import com.algaworks.algafood.dto.entrada.UsuarioEntradaSemSenhaDTO;
import com.algaworks.algafood.dto.retorno.UsuarioRetornoDTO;
import com.algaworks.algafood.entity.Usuario;

@Component
public class UsuarioConversor {
	
	@Autowired
	private ModelMapper modelMapper;

	public UsuarioRetornoDTO converterParaDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioRetornoDTO.class);
	}
	
	public List<UsuarioRetornoDTO> converterListaParaDTO(List<Usuario> usuarios) {
		return usuarios.stream()
						 .map(usuario -> converterParaDTO(usuario))
						 .collect(Collectors.toList());
	}
	
	public Usuario converterParaObjeto(UsuarioEntradaDTO dto) {
		return modelMapper.map(dto, Usuario.class);
	}
	
	public void copiarParaObjeto(UsuarioEntradaSemSenhaDTO dto, Usuario	usuario) {
		modelMapper.map(dto, usuario);
	}
	
}
