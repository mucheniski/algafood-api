package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.FormaPagamentoDTO;
import com.algaworks.algafood.entity.FormaPagamento;

@Component
public class FormaPagamentoConversor {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO converterParaDTO(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> converterListaParaDTO(List<FormaPagamento> formasPagamento) {
		return formasPagamento.stream()
						.map(formaPagamento -> converterParaDTO(formaPagamento))
						.collect(Collectors.toList());
	}
	
	public FormaPagamento converterParaObjeto(FormaPagamentoDTO formaPagamentoDTO) {
		return modelMapper.map(formaPagamentoDTO, FormaPagamento.class);
	}
	
	public void copiarParaObjeto(FormaPagamentoDTO formaPagamentoDTO, FormaPagamento formaPagamento) {
		formaPagamentoDTO.setId(formaPagamento.getId());		
		modelMapper.map(formaPagamentoDTO, formaPagamento);
	}

}
