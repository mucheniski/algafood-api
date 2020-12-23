package com.algaworks.algafood.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Pedido buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
	}
	
	public PedidoDTO buscarDtoPorId(Long id) {
		Pedido pedido = buscarPorId(id);
		return conversor.converterParaDTO(pedido);		
	}

	public List<PedidoResumoDTO> listar() {
		return pedidoResumoConversor.converterListaParaDTO(repository.buscarTodosResumido());
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
		pedido.statusCriado();
		
		return repository.save(pedido);
	}
	
	/*
	 * Como a operação está dentro de um transactional já vai ser feito o commit, não precisa de save
	 */
	@Transactional
	public void confirmarPedido(Long id) {
		Pedido pedido = buscarPorId(id);
		pedido.statusConfirmado();		
	}
	
	@Transactional
	public void confirmarEntrega(Long id) {
		Pedido pedido = buscarPorId(id);
		pedido.stausEntregue();
	}
	
	@Transactional
	public void cancelarPedido(Long id) {
		Pedido pedido = buscarPorId(id);
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
