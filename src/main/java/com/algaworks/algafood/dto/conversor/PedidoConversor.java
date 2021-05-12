package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.controller.*;
import com.algaworks.algafood.links.LinkManager;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.PedidoDTO;
import com.algaworks.algafood.entity.Pedido;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoConversor extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LinkManager linkManager;

	public PedidoConversor() {
		super(PedidoController.class, PedidoDTO.class);
	}

	@Override
	public PedidoDTO toModel(Pedido pedido) {
		var pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);
		pedidoDTO = linkManager.linkToPedido(pedidoDTO, pedido);
		return pedidoDTO;
	}
	
	public List<PedidoDTO> toCollectionModel(List<Pedido> pedidos) {
		return pedidos.stream()
						.map(pedido -> toModel(pedido))
						.collect(Collectors.toList());
	}
	
	public Pedido converterParaObjeto(PedidoDTO pedidoDTO) {
		return modelMapper.map(pedidoDTO, Pedido.class);
	}
	
	public void copiarParaObjeto(PedidoDTO pedidoDTO, Pedido pedido) {
		modelMapper.map(pedidoDTO, pedido);
	}
	
}
