package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.GrupoDTO;
import com.algaworks.algafood.service.UsuarioService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public List<GrupoDTO> listarGruposPorUsuario(@PathVariable Long usuarioId) {
		return usuarioService.listarGruposPorUsuario(usuarioId);
	}
	
	@PutMapping("/{grupoId}/vincular")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void vincularGrupoAoUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.vincularGrupoAoUsuario(usuarioId, grupoId);
	}
	
	@PutMapping("/{grupoId}/desvincular")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desvincularGrupoAoUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.desvincularGrupoAoUsuario(usuarioId, grupoId);
	}

}
