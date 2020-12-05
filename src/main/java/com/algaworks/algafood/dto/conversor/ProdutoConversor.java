package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.ProdutoDTO;
import com.algaworks.algafood.entity.Produto;

@Component
public class ProdutoConversor {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoDTO converterParaDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> converterListaParaDTO(List<Produto> produtos) {
		return produtos.stream()
						.map(produto -> converterParaDTO(produto))
						.collect(Collectors.toList());
	}
	
	public Produto converterParaObjeto(ProdutoDTO produtoDTO) {
		return modelMapper.map(produtoDTO, Produto.class);
	}
	
	public void copiarParaObjeto(ProdutoDTO produtoDTO, Produto produto) {		
		/**
		 * Precisa copiar o id para evitar esse erro
		 * HHH000346: Error during managed flush [org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.entity.Produto was altered from 4 to null]
		   Resolved [org.springframework.orm.jpa.JpaSystemException: identifier of an instance of com.algaworks.algafood.entity.Produto was altered from 4 to null; nested exception is org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.entity.Produto was altered from 4 to null]
		 */
		produtoDTO.setId(produto.getId());
		modelMapper.map(produtoDTO, produto);
	}

}
