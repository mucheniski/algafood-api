package com.algaworks.algafood.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.dto.PedidoDTO;
import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.dto.conversor.PedidoConversor;
import com.algaworks.algafood.dto.conversor.PedidoResumoConversor;
import com.algaworks.algafood.entity.Cidade;
import com.algaworks.algafood.entity.FormaPagamento;
import com.algaworks.algafood.entity.Pedido;
import com.algaworks.algafood.entity.Produto;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.entity.Usuario;
import com.algaworks.algafood.exception.FormaPagamentoNaoValidadaException;
import com.algaworks.algafood.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.repository.PedidoRepository;
import com.algaworks.algafood.filtro.PedidoFiltro;
import com.algaworks.algafood.repository.spec.PedidoSpecs;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private PedidoConversor conversor;
	
	@Autowired
	private PedidoResumoConversor pedidoResumoConversor;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private ProdutoService produtoService;

	public Pedido buscarPorCodigo(String codigo) {
		return repository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
	}
	
	public PedidoDTO buscarDtoPorCodigo(String codigo) {
		Pedido pedido = buscarPorCodigo(codigo);
		return conversor.converterParaDTO(pedido);		
	}

	public Page<PedidoResumoDTO> listar(Pageable pageable) {
		Page<Pedido> pedidosPaginado = repository.findAll(pageable);
		List<PedidoResumoDTO> pedidosDTO = pedidoResumoConversor.converterListaParaDTO(pedidosPaginado.getContent());
		Page<PedidoResumoDTO> pedidosDTOPaginado = new PageImpl<>(pedidosDTO, pageable, pedidosPaginado.getTotalElements());
		return pedidosDTOPaginado;
	}
	
	public List<PedidoResumoDTO> pesquisarComFiltro(PedidoFiltro filtro) {
		return pedidoResumoConversor.converterListaParaDTO(repository.findAll(PedidoSpecs.comFiltro(filtro)));
	}

	public PedidoResumoDTO criarPedido(PedidoDTO dto) {			
		Pedido pedido = conversor.converterParaObjeto(dto);		
		pedido = emitirPedido(pedido);		
		return conversor.converterParaDTOResumido(pedido);		
	}
	
	@Transactional
	private Pedido emitirPedido(Pedido pedido) {
		
		validaPedido(pedido);
		validaItens(pedido);

		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();		
		
		return repository.save(pedido);
	}
	
	/*
	 * Como a operação está dentro de um transactional já vai ser feito o commit, não precisa de save
	 */
	@Transactional
	public void confirmarPedido(String codigo) {
		Pedido pedido = buscarPorCodigo(codigo);
		pedido.statusConfirmado();		
	}
	
	@Transactional
	public void confirmarEntrega(String codigo) {
		Pedido pedido = buscarPorCodigo(codigo);
		pedido.stausEntregue();
	}
	
	@Transactional
	public void cancelarPedido(String codigo) {
		Pedido pedido = buscarPorCodigo(codigo);
		pedido.stausCancelado();
	}

	private void validaPedido(Pedido pedido) {
		
		// TODO: buscar o cliente logado
		Usuario usuarioCliente = usuarioService.buscarPorId(1L);		
		pedido.setUsuarioCliente(usuarioCliente);
		
		Cidade cidade = cidadeService.buscarPorId(pedido.getEnderecoEntrega().getCidade().getId());
		Restaurante restaurante = restauranteService.buscarPorId(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(pedido.getFormaPagamento().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		
		if (!restaurante.validaFormaDePagamento(formaPagamento)) {
			throw new FormaPagamentoNaoValidadaException(restaurante, formaPagamento);
		}
		
	}
	
	private void validaItens(Pedido pedido) {
		
		pedido.getItens().forEach(item -> {
			Produto produto = produtoService.buscarPorId(item.getProduto().getId());
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
		
	}
	
}
