package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.PedidoDTO;
import com.algaworks.algafood.entity.Pedido;

@Component
public class PedidoConversor {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoDTO converterParaDTO(Pedido pedido) {		
		return modelMapper.map(pedido, PedidoDTO.class);
	}
	
	public List<PedidoDTO> converterListaParaDTO(List<Pedido> pedidos) {
		return pedidos.stream()
						.map(pedido -> converterParaDTO(pedido))
						.collect(Collectors.toList());
	}
	
	public Pedido converterParaObjeto(PedidoDTO pedidoDTO) {
		return modelMapper.map(pedidoDTO, Pedido.class);
	}
	
//	public void copiarParaObjeto(PedidoDTO pedidoDTO, Pedido pedido) {
//		pedidoDTO.setId(pedido.getId());		
//		pedidoDTO.setEstado(estadoConversor.converterParaDTO(pedido.getEstado()));
//		modelMapper.map(pedidoDTO, pedido);
//	}
	
}
