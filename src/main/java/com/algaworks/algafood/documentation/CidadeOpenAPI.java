package com.algaworks.algafood.documentation;

import com.algaworks.algafood.dto.CidadeDTO;
import com.algaworks.algafood.dto.input.CidadeInputDTO;
import com.algaworks.algafood.exception.Problema;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
public interface CidadeOpenAPI {
    @ApiOperation("Lista as cidades")
    List<CidadeDTO> listar();

    @ApiOperation("Busca uma cidade por id")
    /*
     * Quando é algúm retorno mais específico do método e não é tratado de forma Global no SpringFoxConfig, deve ser implementado diretamente no controller com
     * o @ApiRresponse que recebe um array de responses
     * */
    @ApiResponses({
            @ApiResponse(code = 400, message = "Parametro passado inválido", response = Problema.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)
    })
    CidadeDTO buscarPorId(@ApiParam(value = "id de uma cidade", example = "1") Long id);

    @ApiOperation("Cadastra uma cidade")
    /*
     * No caso do 201 created não precisamos colocar response porque o retorno do método já é uma CidadeDTO, então o SpringFox já sabe o que retornar
     * */
    @ApiResponses({
            @ApiResponse(code = 201, message = "Criado com sucesso")
    })
    CidadeDTO salvar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") CidadeInputDTO dto);

    @ApiOperation("Atualiza uma cidade por id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
    })
    CidadeDTO atualizar(
            @ApiParam(value = "id de uma cidade", example = "1") Long id,
            @ApiParam(name = "corpo", value = "Representação de uma cidade atualizada") CidadeDTO dto
    );

    @ApiOperation("Exclui uma cidade por ai")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
    })
    void remover(@ApiParam(value = "id de uma cidade", example = "1") Long id);
}
