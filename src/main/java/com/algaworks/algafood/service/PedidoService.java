package com.algaworks.algafood.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
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
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

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

//	public List<PedidoResumoDTO> listar() {
//		return pedidoResumoConversor.converterListaParaDTO(repository.buscarTodosResumido());
//	}
	
	public MappingJacksonValue listar(String campos) {		
		List<PedidoResumoDTO> pedidosDto = pedidoResumoConversor.converterListaParaDTO(repository.buscarTodosResumido());
		
		
		/*
		 * Por padrão serializa tudo, mas se os campos estiverem preenchidos ele substitui pelo mesmo nome de filtro
		 * com o filterOutAllExcept apontando apenas os campos que devem retornar.
		 */
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();	
		filterProvider.addFilter("pedidoFiltro", SimpleBeanPropertyFilter.serializeAll());
		
		if (StringUtils.isNotBlank(campos)) {
			filterProvider.addFilter("pedidoFiltro", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}
		
		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDto);
		pedidosWrapper.setFilters(filterProvider);
		
		return pedidosWrapper;
		
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
