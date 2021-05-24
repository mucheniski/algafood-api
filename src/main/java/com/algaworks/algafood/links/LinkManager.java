package com.algaworks.algafood.links;

import com.algaworks.algafood.controller.*;
import com.algaworks.algafood.dto.*;
import com.algaworks.algafood.entity.Estado;
import com.algaworks.algafood.entity.Pedido;
import com.algaworks.algafood.entity.Restaurante;
import lombok.var;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// TODO: fazer os desafios à partir da aula 19.24
@Component
public class LinkManager {

    public PedidoResumoDTO linkToPedidoResumo(PedidoResumoDTO pedidoResumoDto, Pedido pedido) {

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

    public PedidoDTO linkToPedido(PedidoDTO pedidoDTO, Pedido pedido) {
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

        if (pedido.podeSerConfirmado()) {
            var linkConfirmarPedido = linkTo(methodOn(PedidoController.class).confirmarPedido(pedido.getCodigo())).withRel("/confirmar-pedido");
            pedidoDTO.add(linkConfirmarPedido);
        }

        if (pedido.podeSerEntregue()) {
            var linkConfirmarEntrega = linkTo(methodOn(PedidoController.class).confirmarEntrega(pedido.getCodigo())).withRel("/confirmar-entrega");
            pedidoDTO.add(linkConfirmarEntrega);
        }

        if (pedido.podeSerCancelado()) {
            var linkCancelarPedido = linkTo(methodOn(PedidoController.class).cancelarPedido(pedido.getCodigo())).withRel("/cancelar-pedido");
            pedidoDTO.add(linkCancelarPedido);
        }

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

    public EstadoDTO linkToEstado(EstadoDTO estadoDTO) {

        Link linkBuscarPorId = linkTo(methodOn(EstadoController.class).buscarPorId(estadoDTO.getId())).withSelfRel();
        Link linkListar = linkTo(methodOn(EstadoController.class).listar()).withRel("lista");

        estadoDTO.add(linkBuscarPorId);
        estadoDTO.add(linkListar);

        return estadoDTO;

    }

    public RestauranteRetornoDTO linkToRestaurante(RestauranteRetornoDTO restauranteRetornoDTO, Restaurante restaurante) {
        var linkBuscarPorId = linkTo(methodOn(RestauranteController.class).buscarPorId(restaurante.getId())).withSelfRel();
        var linkListar = linkTo(methodOn(RestauranteController.class).listar()).withRel("listar");

        restauranteRetornoDTO.add(linkBuscarPorId);
        restauranteRetornoDTO.add(linkListar);

        return restauranteRetornoDTO;
    }

    public UsuarioDTO linkToUsuario(UsuarioDTO usuarioDTO) {
        Link linkBuscarPorId = linkTo(methodOn(UsuarioController.class).buscarPorId(usuarioDTO.getId())).withSelfRel();
        Link linkListar = linkTo(methodOn(UsuarioController.class).listar()).withRel("lista");
        Link linkGrupos = linkTo(methodOn(UsuarioGrupoController.class).listarGruposPorUsuario(usuarioDTO.getId())).withSelfRel();

        usuarioDTO.add(linkBuscarPorId);
        usuarioDTO.add(linkListar);
        usuarioDTO.add(linkGrupos);

        return usuarioDTO;
    }

}
