package com.algaworks.algafood.documentation;

import com.algaworks.algafood.dto.PedidoDTO;
import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.filtro.PedidoFiltro;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface PedidoOpenAPI {

    PedidoDTO buscarDtoPorCodigo(@PathVariable String codigo);

    Page<PedidoResumoDTO> listar(Pageable pageable);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "fields",
                              value = "Nomes das propriedades que deseja filtrar no response, separados por vírgula, usam o squiggly",
                              paramType = "query",
                              type = "string"
            )
    })
    List<PedidoResumoDTO> pesquisarComFiltro(PedidoFiltro filtro);

    PedidoResumoDTO criarPedido(@Valid @RequestBody PedidoDTO dto);

    void confirmarPedido(@PathVariable String codigo);

    void confirmarEntrega(@PathVariable String codigo);

    void cancelarPedido(@PathVariable String codigo);
}
