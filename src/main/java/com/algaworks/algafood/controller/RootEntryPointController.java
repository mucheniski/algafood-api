package com.algaworks.algafood.controller;

import com.algaworks.algafood.links.LinkManager;
import com.algaworks.algafood.model.RootEntryPointModel;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @GetMapping
    public RootEntryPointModel root() {

        var rootEntryPointModel = new RootEntryPointModel();

        // TODO: Substituir todas as strings como no estados por properties no messages.properties
        var linkToCozinhas = linkTo(methodOn(CozinhaController.class).listarSemPaginacao()).withRel("cozinhas/sem-paginacao");
        var linkToPedidos = linkTo(methodOn(PedidoController.class).listarCompleto()).withRel("pedidos");
        var linkToRestaurantes = linkTo(methodOn(RestauranteController.class).listar()).withRel("restaurantes");
        var linkToGrupos = linkTo(methodOn(GrupoController.class).listar()).withRel("grupos");
        var linkToUsuarios = linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios");
        var linkToEstados = linkTo(methodOn(EstadoController.class).listar()).withRel("estados");
        var linkToCidades = linkTo(methodOn(CidadeController.class).listar()).withRel("cidades");

        rootEntryPointModel.add(linkToCozinhas);
        rootEntryPointModel.add(linkToPedidos);
        rootEntryPointModel.add(linkToRestaurantes);
        rootEntryPointModel.add(linkToGrupos);
        rootEntryPointModel.add(linkToUsuarios);
        rootEntryPointModel.add(linkToEstados);
        rootEntryPointModel.add(linkToCidades);

        return rootEntryPointModel;

    }

}
