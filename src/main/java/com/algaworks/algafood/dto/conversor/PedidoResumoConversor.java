package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.dto.PedidoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.entity.Pedido;

@Component
public class PedidoResumoConversor {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoDTO converterParaDTO(Pedido pedido) {		
		return modelMapper.map(pedido, PedidoResumoDTO.class);
	}
	
	public List<PedidoResumoDTO> converterListaParaDTO(List<Pedido> pedidos) {
		return pedidos.stream()
						.map(pedido -> converterParaDTO(pedido))
						.collect(Collectors.toList());
	}
	
	public Pedido converterParaObjeto(PedidoResumoDTO pedidoDTO) {
		return modelMapper.map(pedidoDTO, Pedido.class);
	}
	
//	public void copiarParaObjeto(PedidoDTO pedidoDTO, Pedido pedido) {
//		pedidoDTO.setId(pedido.getId());		
//		pedidoDTO.setEstado(estadoConversor.converterParaDTO(pedido.getEstado()));
//		modelMapper.map(pedidoDTO, pedido);
//	}
	
}
