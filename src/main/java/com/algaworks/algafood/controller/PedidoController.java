package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.dto.PedidoDTO;
import com.algaworks.algafood.dto.PedidoResumoDTO;
import com.algaworks.algafood.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService service;
	
	@GetMapping("/{id}")
	public PedidoDTO buscarDtoPorId(@PathVariable Long id) {
		return service.buscarDtoPorId(id);
	}
	
	@GetMapping
	public List<PedidoResumoDTO> listar() {
		return service.listar();
	}
	
	@PostMapping
	public PedidoResumoDTO criarPedido(@Valid @RequestBody PedidoDTO dto) {
		return service.criarPedido(dto);
	}
	
	@PutMapping("/{id}/confirmar-pedido")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmarPedido(@PathVariable Long id) {
		service.confirmarPedido(id);
	}
	
}
