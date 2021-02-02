package com.algaworks.algafood.service;

import com.algaworks.algafood.dto.FotoProdutoDTO;
import com.algaworks.algafood.dto.FotoProdutoPutDTO;
import com.algaworks.algafood.dto.ProdutoDTO;
import com.algaworks.algafood.dto.RestauranteRetornoDTO;
import com.algaworks.algafood.dto.conversor.FotoProdutoConversor;
import com.algaworks.algafood.dto.conversor.ProdutoConversor;
import com.algaworks.algafood.dto.conversor.RestauranteConversor;
import com.algaworks.algafood.entity.FotoProduto;
import com.algaworks.algafood.entity.Produto;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.RestauranteNaoEncotradoException;
import com.algaworks.algafood.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestauranteProdutoService {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private RestauranteConversor conversor;

    @Autowired
    private ProdutoConversor produtoConversor;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FotoProdutoConversor fotoProdutoConversor;

    public RestauranteRetornoDTO buscarDtoPorId(Long id) {
        Restaurante restaurante = buscarPorId(id);
        return conversor.converterParaDTO(restaurante);
    }

    public Restaurante buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RestauranteNaoEncotradoException(id) );
    }

    public List<ProdutoDTO> listarProdutos(Long restauranteId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        List<Produto> produtos = produtoService.buscaApenasAtivosPorRestaurante(restaurante);
        return produtoConversor.converterListaParaDTO(produtos);
    }

    public List<ProdutoDTO> listarProdutosOpcaoInativo(Long restauranteId, boolean incluirInativos) {
        List<Produto> todosProdutos = new ArrayList<>();
        Restaurante restaurante = buscarPorId(restauranteId);

        if (incluirInativos) {
            todosProdutos = produtoService.buscarPorRestaurante(restaurante);
        }
        else {
            todosProdutos = produtoService.buscaApenasAtivosPorRestaurante(restaurante);
        }

        return produtoConversor.converterListaParaDTO(todosProdutos);
    }

    public ProdutoDTO buscarProdutoDTOPorRestaurante(Long restauranteId, Long produtoId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        Produto produto = produtoService.buscarProdutoPorRestaurante(restaurante, produtoId);
        return produtoConversor.converterParaDTO(produto);
    }

    public Produto buscarProdutoPorRestaurante(Long restauranteId, Long produtoId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        return produtoService.buscarProdutoPorRestaurante(restaurante, produtoId);
    }

    @Transactional
    public ProdutoDTO adicionarProduto(ProdutoDTO produtoDTO, Long restauranteId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        Produto produto = produtoConversor.converterParaObjeto(produtoDTO);
        produto.setRestaurante(restaurante);
        produtoService.salvar(produto);
        restaurante.adicionarProduto(produto);
        return produtoConversor.converterParaDTO(produto);
    }

    @Transactional
    public ProdutoDTO atualizarProduto(Long restauranteId, Long produtoId, ProdutoDTO produtoDTO) {
        Restaurante restaurante = buscarPorId(restauranteId);
        Produto produto = produtoService.buscarProdutoPorRestaurante(restaurante, produtoId);
        produtoConversor.copiarParaObjeto(produtoDTO, produto);
        return produtoConversor.converterParaDTO(produto);
    }

    @Transactional
    public FotoProdutoDTO salvarFotoProduto(Long restauranteId, Long produtoId, FotoProdutoPutDTO fotoProdutoPutDTO) {
        apagaFotoExistente(restauranteId, produtoId);
        FotoProduto fotoProduto = salvarFotoNova(restauranteId, produtoId, fotoProdutoPutDTO);
        return fotoProdutoConversor.converterParaDTO(fotoProduto);
    }

    private void apagaFotoExistente(Long restauranteId, Long produtoId) {
        Optional<FotoProduto> fotoExistente = produtoService.buscarFotoPorId(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            produtoService.apagaFotoProduto(fotoExistente.get());
        }
    }

    private FotoProduto salvarFotoNova(Long restauranteId, Long produtoId, FotoProdutoPutDTO fotoProdutoPutDTO) {
        Produto produto = buscarProdutoPorRestaurante(restauranteId, produtoId);

        MultipartFile arquivo = fotoProdutoPutDTO.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoPutDTO.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        fotoProduto = produtoService.salvarFotoProduto(fotoProduto);
        return fotoProduto;
    }

}
