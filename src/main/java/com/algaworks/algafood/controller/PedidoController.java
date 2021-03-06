package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.documentation.PedidoOpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.algaworks.algafood.filtro.PedidoFiltro;
import com.algaworks.algafood.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoOpenAPI {

	@Autowired
	private PedidoService service;
	
	@Override
	@GetMapping("/{codigo}")
	public PedidoDTO buscarDtoPorCodigo(@PathVariable String codigo) {
		return service.buscarDtoPorCodigo(codigo);
	}
	
	@Override
	@GetMapping("/listar-resumido")
	public PagedModel<PedidoResumoDTO> listarResumido(Pageable pageable) {
		return service.listarResumido(pageable);
	}

	@Override
	@GetMapping("/listar-completo")
	public List<PedidoDTO> listarCompleto() {
		return service.listarCompleto();
	}
	
	/*
	 * Só pelo fato de colocar o PedidoFiltro como parametro, o spring já instancia um PedidoFiltro
	 * e atribui quando passado na url um clienteId e restauranteId a esse PedidoFiltro criado automaticamente.
	 * pois são atributos do PedidoFiltro, assim jã são identificados.
	 */
	@Override
	@GetMapping("/com-filtro")
	public List<PedidoResumoDTO> pesquisarComFiltro(PedidoFiltro filtro) {
		return service.pesquisarComFiltro(filtro);
	}
	
	@Override
	@PostMapping
	public PedidoResumoDTO criarPedido(@Valid @RequestBody PedidoDTO dto) {
		return service.criarPedido(dto);
	}
	
	@Override
	@PutMapping("/{codigo}/confirmar-pedido")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmarPedido(@PathVariable String codigo) {
		service.confirmarPedido(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{codigo}/confirmar-entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmarEntrega(@PathVariable String codigo) {
		service.confirmarEntrega(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{codigo}/cancelar-pedido")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelarPedido(@PathVariable String codigo) {
		service.cancelarPedido(codigo);
		return ResponseEntity.noContent().build();
	}
	
}
