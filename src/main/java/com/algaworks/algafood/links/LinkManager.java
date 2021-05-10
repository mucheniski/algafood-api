package com.algaworks.algafood.links;

import com.algaworks.algafood.controller.PedidoController;
import com.algaworks.algafood.controller.RestauranteController;
import com.algaworks.algafood.controller.UsuarioController;
import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.entity.Pedido;
import lombok.var;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LinkManager {

    public PedidoResumoDTO linkToPedidos(PedidoResumoDTO pedidoResumoDto, Pedido pedido) {

        // Pedido
        var linkBuscarPorId = linkTo(methodOn(PedidoController.class).buscarDtoPorCodigo(pedido.getCodigo())).withSelfRel();
        var linkListarCompleto = linkTo(methodOn(PedidoController.class).listarCompleto()).withRel("lista-completa");

        var variables = new TemplateVariables(
                new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        var filterVariables = new TemplateVariables(
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        var linkListaResumida = new Link(UriTemplate.of(pedidosUrl, variables.concat(filterVariables)), "lista-resumida");

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

}
