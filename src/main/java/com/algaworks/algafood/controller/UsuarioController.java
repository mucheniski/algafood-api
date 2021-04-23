package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.UsuarioAtualizaSenhaDTO;
import com.algaworks.algafood.dto.UsuarioEntradaDTO;
import com.algaworks.algafood.dto.UsuarioEntradaSemSenhaDTO;
import com.algaworks.algafood.dto.UsuarioDTO;
import com.algaworks.algafood.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public CollectionModel<UsuarioDTO> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public UsuarioDTO buscarPorId(@PathVariable Long id) {
		return service.buscarDtoPorId(id);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public UsuarioDTO salvar(@RequestBody @Valid UsuarioEntradaDTO dto) {
		return service.salvar(dto);
	}
	
	@PutMapping("/{id}")
	public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioEntradaSemSenhaDTO dto) {
		return service.atualizar(id, dto);
	}
	
	@PutMapping("/{id}/atualiza-senha")
	public UsuarioDTO atualizaSenha(@PathVariable Long id, @RequestBody @Valid UsuarioAtualizaSenhaDTO dto) {
		return service.atualizaSenha(id, dto);
	}

}
