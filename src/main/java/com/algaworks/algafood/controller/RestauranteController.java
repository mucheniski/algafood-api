package com.algaworks.algafood.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.EntidadeNaoEncotradaException;
import com.algaworks.algafood.repository.RestauranteRepository;
import com.algaworks.algafood.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping	
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncotradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restauranteRecebida) {
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);
		if (restauranteAtual.isPresent()) {	
			try {						
				BeanUtils.copyProperties(restauranteRecebida, restauranteAtual.get(), "id"); // Do terceiro parâmetro em diante passamos o que queremos que seja ignorado
				Restaurante restauranteSalva = restauranteService.salvar(restauranteAtual.get());
				return ResponseEntity.ok(restauranteSalva);			
			} catch (EntidadeNaoEncotradaException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		return ResponseEntity.notFound().build();		
	}	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> camposAtualizados) {		
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);  
		if (!restauranteAtual.isPresent()) {
			return ResponseEntity.notFound().build();
		}		
		mergeCampos(camposAtualizados, restauranteAtual.get());
		
		return atualizar(id, restauranteAtual.get());
	}

	private void mergeCampos(Map<String, Object> camposAtualizados, Restaurante restauranteAtual) {
		ObjectMapper objectMapper = new ObjectMapper(); // Converte JSON em Java e Java em JSON		
		Restaurante restauranteOrigem = objectMapper.convertValue(camposAtualizados, Restaurante.class);		
		
		System.out.println(restauranteOrigem);
		
		camposAtualizados.forEach((chaveCampo, valorCampo) -> {
			Field campo = ReflectionUtils.findField(Restaurante.class, chaveCampo); // Busca na classe Restaurante um campo com o nome passado na chaveCampo
			campo.setAccessible(true); // Torna a variável que é private acessível aqui.			
			Object novoValor = ReflectionUtils.getField(campo, restauranteOrigem);		
			ReflectionUtils.setField(campo, restauranteAtual, novoValor);			
		});
	}
	
	
}
