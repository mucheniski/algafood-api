package com.algaworks.algafood.service;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.RestauranteNaoEncotradaException;
import com.algaworks.algafood.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestauranteService {
	
	private static final String MSG_RESTAURANTE_EM_USO = "	Restaurante id %d não pode ser removida, está em uso!";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	public Restaurante buscarPorId(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncotradaException(restauranteId) );
	}
	
	public Restaurante salvar(Restaurante restaurante) {
		Cozinha cozinha = cozinhaService.buscarPorId(restaurante.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}
	
	public void remover(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncotradaException(restauranteId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		
		}
	}
	
	public void mergeCampos(Map<String, Object> camposAtualizados, Restaurante restauranteAtual, HttpServletRequest request) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			
			ObjectMapper objectMapper = new ObjectMapper(); // Converte JSON em Java e Java em JSON		
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true); // Confiruado para dar erro quando for passado algum objeto que está marcado com @Jsonignore
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true); // Falha caso a propriedade não exista
			Restaurante restauranteOrigem = objectMapper.convertValue(camposAtualizados, Restaurante.class);		
			
			System.out.println(restauranteOrigem);
			
			camposAtualizados.forEach((chaveCampo, valorCampo) -> {
				Field campo = ReflectionUtils.findField(Restaurante.class, chaveCampo); // Busca na classe Restaurante um campo com o nome passado na chaveCampo
				campo.setAccessible(true); // Torna a variável que é private acessível aqui.			
				Object novoValor = ReflectionUtils.getField(campo, restauranteOrigem);		
				ReflectionUtils.setField(campo, restauranteAtual, novoValor);			
			});
			
		} catch (IllegalArgumentException e) {
			Throwable causaRaiz = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), causaRaiz, serverHttpRequest);
		}
		
	}
	
}
