package com.algaworks.algafood.documentation;

import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.exception.Problema;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cozinhas")
public interface CozinhaOpenAPI {

    @ApiOperation("Lista os registros")
    Page<CozinhaDTO> listar(Pageable pageable);

    @ApiOperation("Lista os registros por nome")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Parametro passado inválido", response = Problema.class),
            @ApiResponse(code = 404, message = "Não encontrado", response = Problema.class)
    })
    List<CozinhaDTO> listarPorNome(String nome);

    @ApiOperation("Busca por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Parametro passado inválido", response = Problema.class),
            @ApiResponse(code = 404, message = "Não encontrado", response = Problema.class)
    })
    CozinhaDTO buscarPorId(@ApiParam(value = "ID", example = "1") Long id);

    @ApiOperation("Buscar o primeiro registro")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Não encontrado", response = Problema.class)
    })
    CozinhaDTO bucarPrimeiro();

    @ApiOperation("Cadastra um novo registro")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Criado com sucesso")
    })
    CozinhaDTO salvar(CozinhaDTO dto);

    @ApiOperation("Atualiza um registro por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
    })
    CozinhaDTO atualizar(
            @ApiParam(value = "ID", example = "1") Long id,
            @ApiParam(name = "corpo", value = "Representação de um regsitro atualizado") CozinhaDTO dto
    );

    @ApiOperation("Exclui um registro por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
    })
    void remover(@ApiParam(value = "ID", example = "1") Long id);
}
