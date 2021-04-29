package com.algaworks.algafood.dto.conversor;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.controller.*;
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

	public PedidoConversor() {
		super(PedidoController.class, PedidoDTO.class);
	}

	@Override
	public PedidoDTO toModel(Pedido pedido) {
		var pedidoDTO = modelMapper.map(pedido, PedidoDTO.class);

		// Pedido
		var linkBuscarPorId = linkTo(methodOn(PedidoController.class).buscarDtoPorCodigo(pedido.getCodigo())).withSelfRel();
		var linkListar = linkTo(methodOn(PedidoController.class).listarCompleto()).withRel("lista");

		// Usuario
		var linkUsuario = linkTo(methodOn(UsuarioController.class).buscarPorId(pedido.getUsuarioCliente().getId())).withSelfRel();

		// Cidade
		var linkCidade = linkTo(methodOn(CidadeController.class).buscarPorId(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel();

		// Restaurante
		var linkRestaurante = linkTo(methodOn(RestauranteController.class).buscarPorId(pedido.getRestaurante().getId())).withSelfRel();

		// Forma Pagamento
		var linkFormaPagamento = linkTo(methodOn(FormaPagamentoController.class).buscarPorId(pedido.getFormaPagamento().getId())).withSelfRel();

		pedidoDTO.add(linkBuscarPorId);
		pedidoDTO.add(linkListar);
		pedidoDTO.getUsuarioCliente().add(linkUsuario);
		pedidoDTO.getEnderecoEntrega().getCidade().add(linkCidade);
		pedidoDTO.getRestaurante().add(linkRestaurante);
		pedidoDTO.getFormaPagamento().add(linkFormaPagamento);

		pedidoDTO.getItens().forEach(itemPedidoDTO -> {
			itemPedidoDTO.add(linkTo(methodOn(RestauranteProdutoController.class).buscarProdutoPorId(pedidoDTO.getRestaurante().getId(), itemPedidoDTO.getProdutoId())).withSelfRel());
		});

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
