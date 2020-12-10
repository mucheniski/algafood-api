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

import com.algaworks.algafood.dto.UsuarioRetornoDTO;
import com.algaworks.algafood.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	@Autowired
	private RestauranteService restauranteService;
	
	@GetMapping
	public List<UsuarioRetornoDTO> listarResponsaveis(@PathVariable Long restauranteId) {
		return restauranteService.listarResponsaveisPorRestaurante(restauranteId);
	}

	@PutMapping("/{usuarioId}/vincular")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void vincularResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.vincularResponsavel(restauranteId, usuarioId);
	}
	
	@PutMapping("/{usuarioId}/desvincular")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desvincularResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desvincularResponsavel(restauranteId, usuarioId);
	}
		
}
