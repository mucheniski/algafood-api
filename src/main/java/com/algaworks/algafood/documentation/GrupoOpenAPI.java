package com.algaworks.algafood.documentation;

import com.algaworks.algafood.dto.GrupoDTO;
import com.algaworks.algafood.exception.Problema;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Grupos")
public interface GrupoOpenAPI {

    @ApiOperation("Lista os grupos")
    List<GrupoDTO> listar();

    @ApiOperation("Busca um grupo por id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Parametro passado inváido", response = Problema.class),
            @ApiResponse(code = 404, message = "Não encontrado", response = Problema.class)
    })
    GrupoDTO buscarPorId(@ApiParam(value = "id de um grupo", example = "1") Long id);

    @ApiOperation("Cadastra um novo grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Criado com sucesso")
    })
    GrupoDTO salvar(@ApiParam(name = "corpo", value = "Representação de um Grupo") GrupoDTO dto);

    @ApiOperation("Atualiza um grupo por id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
    })
    GrupoDTO atualizar(Long id, GrupoDTO dto);

    @ApiOperation("Exclui um grupo por ai")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído com sucesso"),
            @ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
    })
    void remover(Long id);

}
