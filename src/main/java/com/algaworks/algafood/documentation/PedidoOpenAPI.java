package com.algaworks.algafood.documentation;

import com.algaworks.algafood.dto.PedidoDTO;
import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.exception.Problema;
import com.algaworks.algafood.filtro.PedidoFiltro;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Pedidos")
public interface PedidoOpenAPI {

    @ApiOperation("Lista os registros resumidos")
    PagedModel<PedidoResumoDTO> listarResumido(Pageable pageable);

    @ApiOperation("Lista os registros completos")
    List<PedidoDTO> listarCompleto();

    @ApiOperation("Busca um registro por código")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Parametro passado inválido", response = Problema.class),
            @ApiResponse(code = 404, message = "Não encontrado", response = Problema.class)
    })
    PedidoDTO buscarDtoPorCodigo(@ApiParam(value = "codigo", example = "8e6588c2-b393-4e04-ba84-0d8a576977ae") String codigo);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "fields",
                              value = "Nomes das propriedades que deseja filtrar no response, separados por vírgula, usam o squiggly",
                              paramType = "query",
                              type = "string"
            )
    })
    @ApiOperation("Lista os registros com o filtros")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Parametro passado inválido", response = Problema.class),
            @ApiResponse(code = 404, message = "Não encontrado", response = Problema.class)
    })
    List<PedidoResumoDTO> pesquisarComFiltro(PedidoFiltro filtro);

    @ApiOperation("Cadastra um novo registro")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Criado com sucesso")
    })
    PedidoResumoDTO criarPedido(PedidoDTO dto);

    @ApiOperation("Confirma um pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o código", response = Problema.class)
    })
    void confirmarPedido(@ApiParam(value = "codigo", example = "8e6588c2-b393-4e04-ba84-0d8a576977ae") String codigo);

    @ApiOperation("Confirma uma entrega de pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Confirmado com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o código", response = Problema.class)
    })
    void confirmarEntrega(@ApiParam(value = "codigo", example = "8e6588c2-b393-4e04-ba84-0d8a576977ae") String codigo);

    @ApiOperation("Cancela um pedido")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cancelado com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o código", response = Problema.class)
    })
    void cancelarPedido(@ApiParam(value = "codigo", example = "8e6588c2-b393-4e04-ba84-0d8a576977ae") String codigo);

}
