package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.controller.PedidoController;
import com.algaworks.algafood.links.LinkManager;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.entity.Pedido;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoResumoConversor extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LinkManager linkManager;

	public PedidoResumoConversor() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		var pedidoResumoDto = modelMapper.map(pedido, PedidoResumoDTO.class);
		pedidoResumoDto = linkManager.linkToPedidoResumo(pedidoResumoDto, pedido);
		return pedidoResumoDto;
	}
	
	public List<PedidoResumoDTO> toCollectionModel(List<Pedido> pedidos) {
		return pedidos.stream()
						.map(pedido -> toModel(pedido))
						.collect(Collectors.toList());
	}
	
	public Pedido converterParaObjeto(PedidoResumoDTO pedidoDTO) {
		return modelMapper.map(pedidoDTO, Pedido.class);
	}
	
}
