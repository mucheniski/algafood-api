package com.algaworks.algafood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.dto.PedidoDTO;
import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.dto.conversor.PedidoConversor;
import com.algaworks.algafood.dto.conversor.PedidoResumoConversor;
import com.algaworks.algafood.entity.Pedido;
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
	
}
