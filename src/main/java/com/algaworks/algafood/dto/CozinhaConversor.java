package com.algaworks.algafood.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.entity.Cozinha;

@Component
public class CozinhaConversor {
	
	@Autowired
	private ModelMapper modelMapper;

	public CozinhaRetornoDTO converterParaDTO(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaRetornoDTO.class);
	}
	
	public List<CozinhaRetornoDTO> converterListaParaDTO(List<Cozinha> cozinhas) {
		return cozinhas.stream()
						.map(cozinha -> converterParaDTO(cozinha))
						.collect(Collectors.toList());
	}
	
	public Cozinha converterParaObjeto(CozinhaEntradaDTO cozinhaEntradaDTO) {
		return modelMapper.map(cozinhaEntradaDTO, Cozinha.class);
	}
	
	public void copiarParaObjeto(CozinhaEntradaDTO cozinhaEntradaDTO, Cozinha cozinha) {
		cozinhaEntradaDTO.setId(cozinha.getId());
		modelMapper.map(cozinhaEntradaDTO, cozinha);
	}
	
}
