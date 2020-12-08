package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.GrupoDTO;
import com.algaworks.algafood.entity.Grupo;

@Component
public class GrupoConversor {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoDTO converterParaDTO(Grupo grupo) {
		return modelMapper.map(grupo, GrupoDTO.class);
	}
	
	public List<GrupoDTO> converterListaParaDTO(List<Grupo> grupos) {
		return grupos.stream()
						.map(grupo -> converterParaDTO(grupo))
						.collect(Collectors.toList());
	}
	
	public Grupo converterParaObjeto(GrupoDTO dto) {
		return modelMapper.map(dto, Grupo.class);
	}

	public void copiarParaObjeto(GrupoDTO dto, Grupo grupo) {
		modelMapper.map(dto, grupo);		
	}

}
