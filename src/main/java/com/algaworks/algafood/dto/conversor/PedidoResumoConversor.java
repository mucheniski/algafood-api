package com.algaworks.algafood.dto.conversor;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.algafood.controller.CidadeController;
import com.algaworks.algafood.controller.PedidoController;
import com.algaworks.algafood.controller.RestauranteController;
import com.algaworks.algafood.controller.UsuarioController;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.entity.Pedido;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoConversor extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoConversor() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		var pedidoResumoDto = modelMapper.map(pedido, PedidoResumoDTO.class);


		// Pedido
		var linkBuscarPorId = linkTo(methodOn(PedidoController.class).buscarDtoPorCodigo(pedido.getCodigo())).withSelfRel();
		var linkListarCompleto = linkTo(methodOn(PedidoController.class).listarCompleto()).withRel("lista-completa");

		var variables = new TemplateVariables(
				new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
				new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
		);

		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

		var linkListaResumida = new Link(UriTemplate.of(pedidosUrl, variables), "lista-resumida");

		// Usuario
		var linkUsuario = linkTo(methodOn(UsuarioController.class).buscarPorId(pedido.getUsuarioCliente().getId())).withSelfRel();

		// Restaurante
		var linkRestaurante = linkTo(methodOn(RestauranteController.class).buscarPorId(pedido.getRestaurante().getId())).withSelfRel();

		pedidoResumoDto.add(linkBuscarPorId);
		pedidoResumoDto.add(linkListarCompleto);
		pedidoResumoDto.add(linkListaResumida);
		pedidoResumoDto.getUsuarioCliente().add(linkUsuario);
		pedidoResumoDto.getRestaurante().add(linkRestaurante);

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
	
//	public void copiarParaObjeto(PedidoDTO pedidoDTO, Pedido pedido) {
//		pedidoDTO.setId(pedido.getId());		
//		pedidoDTO.setEstado(estadoConversor.converterParaDTO(pedido.getEstado()));
//		modelMapper.map(pedidoDTO, pedido);
//	}
	
}
