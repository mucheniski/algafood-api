package com.algaworks.algafood.documentation;

import com.algaworks.algafood.dto.FormaPagamentoDTO;
import com.algaworks.algafood.exception.Problema;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "FormasPagamento")
public interface FormaPagamentoOpenAPI {

    @ApiOperation("Lista os registros")
    ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request);

    @ApiOperation("Busca por ID")
    /*
     * Quando é algúm retorno mais específico do método e não é tratado de forma Global no SpringFoxConfig, deve ser implementado diretamente no controller com
     * o @ApiRresponse que recebe um array de responses
     * */
    @ApiResponses({
            @ApiResponse(code = 400, message = "Parametro passado inválido", response = Problema.class),
            @ApiResponse(code = 404, message = "Não encontrado", response = Problema.class)
    })
    ResponseEntity<FormaPagamentoDTO> buscarPorIdComETag(Long id, ServletWebRequest request);

    @ApiOperation("Cadastra um novo registro")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Criado com sucesso")
    })
    FormaPagamentoDTO adicionar(FormaPagamentoDTO dto);

    @ApiOperation("Atualiza um registro por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
    })
    FormaPagamentoDTO atualizar(Long id, FormaPagamentoDTO dto);

    @ApiOperation("Exclui um registro por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
    })
    void remover(Long id);

}
