package com.algaworks.algafood.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.entity.Produto;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;

	public Produto buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id)); 
	}
	
	public Produto buscarProdutoPorRestaurante(Restaurante restaurante, Long produtoId) {
		Optional<Produto> produto = repository.buscarProdutoPorRestaurante(restaurante.getId(), produtoId);
		if (produto.isPresent()) {
			return produto.get();
		}		
		throw new ProdutoNaoEncontradoException(produtoId, restaurante.getId());
	}

	public Produto salvar(Produto produto) {
		return repository.save(produto);			
	}
	
}
