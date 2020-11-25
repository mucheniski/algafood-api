package com.algaworks.algafood.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.dto.RestauranteConversor;
import com.algaworks.algafood.dto.RestauranteDTO;
import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.exception.CozinhaNaoEncotradaException;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.EntidadeNaoEncotradaException;
import com.algaworks.algafood.exception.NegocioException;
import com.algaworks.algafood.exception.RestauranteNaoEncotradaException;
import com.algaworks.algafood.repository.CozinhaRepository;
import com.algaworks.algafood.repository.RestauranteRepository;

@Service
public class RestauranteService {
	
	private static final String MSG_RESTAURANTE_EM_USO = "	Restaurante id %d não pode ser removida, está em uso!";
	// TODO: Refatorar os nomes para repository e conversor
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteConversor restauranteConversor;
	
	public List<RestauranteDTO> listar() {
		return restauranteConversor.converterListaParaDTO(restauranteRepository.findAllCustom());
	}
	
	public RestauranteDTO buscarPorId(Long restauranteId) {
		Restaurante restaurante = restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncotradaException(restauranteId) );
		return restauranteConversor.converterParaDTO(restaurante);
	}
	
	public List<RestauranteDTO> listarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteConversor.converterListaParaDTO(restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal));
	}	
	
	public List<RestauranteDTO> listarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteConversor.converterListaParaDTO(restauranteRepository.findByNomeTaxaFrete(nome, taxaInicial, taxaFinal));
	}
	
	public List<RestauranteDTO> comFreteGratis(String nome) {	
		return restauranteConversor.converterListaParaDTO(restauranteRepository.findComFreteGratis(nome));
	}
	
	public List<RestauranteDTO> listarPorNomeECozinha(String nome, Long cozinhaId) {
		return restauranteConversor.converterListaParaDTO(restauranteRepository.consultarPorNome(nome, cozinhaId));
	}
	
	public RestauranteDTO buscarPrimeiro() {
		return restauranteConversor.converterParaDTO(restauranteRepository.buscarPrimeiro().get());
	}
	
	@Transactional
	public RestauranteDTO salvar(RestauranteDTO restauranteDTO) {
		try {
			Restaurante restaurante = restauranteConversor.converterParaObjeto(restauranteDTO);	
			Long cozinhaId = restauranteDTO.getCozinha().getId();
			Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncotradaException(cozinhaId));
			restaurante.setCozinha(cozinha);
			return restauranteConversor.converterParaDTO(restauranteRepository.save(restaurante));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public RestauranteDTO atualizar(Long restauranteId, RestauranteDTO restauranteDTO) {
		try {
			Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncotradaException(restauranteId));
			
			if (restauranteDTO.getCozinha() != null) {
				Long cozinhaId = restauranteDTO.getCozinha().getId();
				Cozinha novaCozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncotradaException(cozinhaId));
				restauranteAtual.setCozinha(novaCozinha);
			}	
			
			restauranteConversor.copiarParaObjeto(restauranteDTO, restauranteAtual);
			return restauranteConversor.converterParaDTO(restauranteRepository.save(restauranteAtual));			
		} catch (EntidadeNaoEncotradaException e) {
			throw new NegocioException(e.getMessage());
		}		
	}
	
	@Transactional
	public void remover(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncotradaException(restauranteId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		
		}
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restaurante = restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncotradaException(restauranteId) );
		
		/*
		 * Não é preciso fazer um save porque à partir do momento que eu busco um registro no banco pelo repository
		 * o contexto de persistência do JPA já gerencia o que foi retornado, assim qualquer alteração que for feita
		 * após esse retorno o próprio spring data JPA jà sincroniza com a base e salva automáticamente.
		 */
		restaurante.ativar();
	}
	
	@Transactional
	public void desativar(Long restauranteId) {
		Restaurante restaurante = restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncotradaException(restauranteId) );
		
		/*
		 * Não é preciso fazer um save porque à partir do momento que eu busco um registro no banco pelo repository
		 * o contexto de persistência do JPA já gerencia o que foi retornado, assim qualquer alteração que for feita
		 * após esse retorno o próprio spring data JPA jà sincroniza com a base e salva automáticamente.
		 */
		restaurante.desativar();
	}
	
}
