package com.algaworks.algafood.service;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.entity.FotoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.entity.Produto;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.repository.ProdutoRepository;
import org.springframework.transaction.annotation.Transactional;

// TODO: Verificar se os métodos ficam aqui ou no RestauranteProdutoService
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

	public List<Produto> buscarPorRestaurante(Restaurante restaurante) {
		return repository.findByRestaurante(restaurante);
	}
		
	public List<Produto> buscaApenasAtivosPorRestaurante(Restaurante restaurante) {
		return repository.buscaApenasAtivosPorRestaurante(restaurante);
	}

	@Transactional
	public FotoProduto salvarFotoProduto(FotoProduto fotoProduto) {
		return repository.salvarFotoProduto(fotoProduto);
	}
}
