package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.dto.input.CidadeInputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.CidadeDTO;
import com.algaworks.algafood.entity.Cidade;

@Component
public class CidadeConversor {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EstadoConversor estadoConversor;

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
		cidadeDTO.setEstado(estadoConversor.converterParaDTO(cidade.getEstado()));
		modelMapper.map(cidadeDTO, cidade);
	}
	
}
