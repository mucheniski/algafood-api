package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.exception.Problema;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.CidadeDTO;
import com.algaworks.algafood.service.CidadeService;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService service;

	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeDTO> listar() {
		return service.listar();
	}

	@ApiOperation("Busca uma cidade por id")
	@GetMapping("/{id}")
	/*
	* Quando é algúm retorno mais específico do método e não é tratado de forma Global no SpringFoxConfig, deve ser implementado diretamente no controller com
	* o @ApiRresponse que recebe um array de responses
	* */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Parametro passado inválido", response = Problema.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problema.class)
	})
	public CidadeDTO buscarPorId(
			@ApiParam(value = "id de uma cidade", example = "1") @PathVariable Long id
	) {
		return service.buscarDtoPorId(id);
	}

	@ApiOperation("Cadastra uma cidade")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	/*
	* No caso do 201 created não precisamos colocar response porque o retorno do método já é uma CidadeDTO, então o SpringFox já sabe o que retornar
	* */
	@ApiResponses({
		@ApiResponse(code = 201, message = "Criado com sucesso")
	})
	public CidadeDTO salvar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody @Valid CidadeDTO dto
	) {
		return service.salvar(dto);
	}

	@ApiOperation("Atualiza uma cidade por id")
	@PutMapping("/{id}")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Atualizado com sucesso"),
		@ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
	})
	public CidadeDTO atualizar(
			@ApiParam(value = "id de uma cidade", example = "1") @PathVariable Long id,
			@ApiParam(name = "corpo", value = "Representação de uma cidade atualizada") @RequestBody @Valid CidadeDTO dto
	) {
		return service.atualizar(id, dto);
	}

	@ApiOperation("Exclui uma cidade por ai")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Excluído com sucesso"),
		@ApiResponse(code = 404, message = "Não encontrado com o id", response = Problema.class)
	})
	public void remover(
			@ApiParam(value = "id de uma cidade", example = "1") @PathVariable Long id
	) {
		service.remover(id);
	}

}
