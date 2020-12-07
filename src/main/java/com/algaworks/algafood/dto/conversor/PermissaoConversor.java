package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.PermissaoDTO;
import com.algaworks.algafood.entity.Permissao;

@Component
public class PermissaoConversor {
	
	@Autowired
	private ModelMapper modelMapper;

	public PermissaoDTO converterParaDTO(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoDTO.class);
	}
	
	public List<PermissaoDTO> converterListaParaDTO(List<Permissao> permissaos) {
		return permissaos.stream()
						 .map(permissao -> converterParaDTO(permissao))
						 .collect(Collectors.toList());
	}
	
	public Permissao converterParaObjeto(PermissaoDTO dto) {
		return modelMapper.map(dto, Permissao.class);
	}
	
	public void copiarParaObjeto(PermissaoDTO dto, Permissao permissao) {
		modelMapper.map(dto, permissao);
	}
	
}
