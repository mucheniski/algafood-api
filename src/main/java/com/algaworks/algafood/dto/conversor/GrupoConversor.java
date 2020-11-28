package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.entrada.GrupoEntradaDTO;
import com.algaworks.algafood.dto.retorno.GrupoRetornoDTO;
import com.algaworks.algafood.entity.Grupo;

@Component
public class GrupoConversor {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoRetornoDTO converterParaDTO(Grupo grupo) {
		return modelMapper.map(grupo, GrupoRetornoDTO.class);
	}
	
	public List<GrupoRetornoDTO> converterListaParaDTO(List<Grupo> grupos) {
		return grupos.stream()
						.map(grupo -> converterParaDTO(grupo))
						.collect(Collectors.toList());
	}
	
	public Grupo converterParaObjeto(GrupoEntradaDTO dto) {
		return modelMapper.map(dto, Grupo.class);
	}

	public void copiarParaObjeto(GrupoEntradaDTO dto, Grupo grupo) {
		modelMapper.map(dto, grupo);		
	}

}
