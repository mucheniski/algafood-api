package com.algaworks.algafood.dto.conversor;

import com.algaworks.algafood.dto.FotoProdutoDTO;
import com.algaworks.algafood.entity.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoConversor {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoDTO converterParaDTO(FotoProduto fotoProduto) {
		return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
	}

	public FotoProduto converterParaObjeto(FotoProdutoDTO fotoProdutoDTO) {
		return modelMapper.map(fotoProdutoDTO, FotoProduto.class);
	}

}
