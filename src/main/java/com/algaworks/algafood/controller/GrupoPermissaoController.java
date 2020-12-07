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

import com.algaworks.algafood.dto.PermissaoDTO;
import com.algaworks.algafood.service.GrupoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@GetMapping
	public List<PermissaoDTO> listaPermissoesPorGrupo(@PathVariable Long grupoId) {		
		return grupoService.listaPermissoesPorGrupo(grupoId);		
	}
	
	@PutMapping("/{permissaoId}/desvincular")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desvinculaPermissao(@PathVariable Long grupoId ,@PathVariable Long permissaoId) {
		grupoService.desvincularPermissao(grupoId, permissaoId);
	}
	
	@PutMapping("/{permissaoId}/vincular")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void vinculaPermissao(@PathVariable Long grupoId ,@PathVariable Long permissaoId) {
		grupoService.vincularPermissao(grupoId, permissaoId);
	}
	
}
