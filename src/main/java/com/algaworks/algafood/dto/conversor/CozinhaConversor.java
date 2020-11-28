package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.entity.Cozinha;

@Component
public class CozinhaConversor {
	
	@Autowired
	private ModelMapper modelMapper;

	public CozinhaDTO converterParaDTO(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}
	
	public List<CozinhaDTO> converterListaParaDTO(List<Cozinha> cozinhas) {
		return cozinhas.stream()
						.map(cozinha -> converterParaDTO(cozinha))
						.collect(Collectors.toList());
	}
	
	public Cozinha converterParaObjeto(CozinhaDTO cozinhaDTO) {
		return modelMapper.map(cozinhaDTO, Cozinha.class);
	}
	
	public void copiarParaObjeto(CozinhaDTO cozinhaDTO, Cozinha cozinha) {
		cozinhaDTO.setId(cozinha.getId());
		modelMapper.map(cozinhaDTO, cozinha);
	}
	
}
