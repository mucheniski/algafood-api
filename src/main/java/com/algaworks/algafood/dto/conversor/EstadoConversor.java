package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.EstadoDTO;
import com.algaworks.algafood.entity.Estado;

@Component
public class EstadoConversor {
	
	@Autowired
	private ModelMapper modelMapper;

	public EstadoDTO converterParaDTO(Estado estado) {
		return modelMapper.map(estado, EstadoDTO.class);
	}
	
	public List<EstadoDTO> converterListaParaDTO(List<Estado> estados) {
		return estados.stream()
						.map(estado -> converterParaDTO(estado))
						.collect(Collectors.toList());
	}
	
	public Estado converterParaObjeto(EstadoDTO estadoDTO) {
		return modelMapper.map(estadoDTO, Estado.class);
	}
	
	public void copiarParaObjeto(EstadoDTO estadoDTO, Estado estado) {
		estadoDTO.setId(estado.getId());
		modelMapper.map(estadoDTO, estado);
	}
	
}
