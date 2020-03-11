package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.entity.Estado;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.EntidadeNaoEncotradaException;
import com.algaworks.algafood.repository.EstadoRepository;
import com.algaworks.algafood.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.listar();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado salvar(@RequestBody Estado estado) {
		return estadoService.salvar(estado);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Estado estadoRecebido) {
		Estado estadoAtual = estadoRepository.buscar(id);
		
		if (estadoAtual != null) {
			try {
				BeanUtils.copyProperties(estadoRecebido, estadoAtual, "id"); // Do terceiro parâmetro em diante passamos o que queremos que seja ignorado
				estadoService.salvar(estadoAtual);
				return ResponseEntity.ok(estadoAtual);
			} catch (EntidadeNaoEncotradaException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> remover(@PathVariable Long id) {
		try {
			estadoService.remover(id);
			return ResponseEntity.noContent().build();	
		} catch (EntidadeNaoEncotradaException e) {
			return ResponseEntity.notFound().build();		
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}