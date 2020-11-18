package com.algaworks.algafood.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.entity.Cidade;
import com.algaworks.algafood.entity.Estado;

@Component
public class CidadeConversor {
	
	@Autowired
	private ModelMapper modelMapper;

	public CidadeDTO converterParaDTO(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDTO.class);
	}
	
	public List<CidadeDTO> converterListaParaDTO(List<Cidade> cidades) {
		return cidades.stream()
						.map(cidade -> converterParaDTO(cidade))
						.collect(Collectors.toList());
	}
	
	public Cidade converterParaObjeto(CidadeDTO cidadeDTO) {
		return modelMapper.map(cidadeDTO, Cidade.class);
	}
	
	public void copiarParaObjeto(CidadeDTO cidadeDTO, Cidade cidade) {
		cidadeDTO.setId(cidade.getId());

		// Mesmo caso citado no RestauranteConversor sobre a instancia de uma nova Cozinha
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeDTO, cidade);
	}
	
}
