package com.algaworks.algafood.service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.entity.FotoProduto;
import com.algaworks.algafood.entity.Produto;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.exception.NegocioException;
import com.algaworks.algafood.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.repository.FotoProdutoRepository;
import com.algaworks.algafood.repository.ProdutoRepository;
import com.algaworks.algafood.service.ArmazenamentoArquivosService.NovaFoto;

import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO: Verificar se os métodos ficam aqui ou no RestauranteProdutoService
@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private FotoProdutoRepository fotoProdutoRepository;

	@Autowired
	private ArmazenamentoArquivosService armazenamentoArquivosService;

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
		var novoNomeArquivo = armazenamentoArquivosService.gerarNovoNome(fotoProduto.getNomeArquivo());
		fotoProduto.setNomeArquivo(novoNomeArquivo);
		var fotoSalva = repository.salvarFotoProduto(fotoProduto);

		// Informa ao JPA para descarregar tudo o que estiver na fila para a base
		repository.flush();

		return fotoSalva;
	}

    public Optional<FotoProduto> buscarFotoPorId(Long restauranteId, Long produtoId) {
		return repository.buscarFotoPorId(restauranteId, produtoId);
    }

    public void apagaFotoProduto(FotoProduto fotoProduto) {
		var nomeFotoAntiga = fotoProduto.getNomeArquivo();
		if (nomeFotoAntiga != null) {
			armazenamentoArquivosService.removerFotoAnterior(fotoProduto.getNomeArquivo());
		}

		repository.apagaFotoProduto(fotoProduto);
	}

	public void armazenarFotoLocal(NovaFoto novaFoto) {
		armazenamentoArquivosService.armazenarFotoLocal(novaFoto);
	}


	public FotoProduto buscarFotoPorProduto(Long produtoId) {
		return fotoProdutoRepository.findById(produtoId).orElseThrow(() -> new FotoProdutoNaoEncontradaException(produtoId));
	}

	public InputStream recuperarFoto(String nomeFoto) {
		return armazenamentoArquivosService.recuperarFoto(nomeFoto);
	}

}
