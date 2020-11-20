package com.algaworks.algafood.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.dto.CozinhaConversor;
import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.exception.CozinhaNaoEncotradaException;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.NegocioException;
import com.algaworks.algafood.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha id %d não pode ser removida, está em uso!";

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaConversor cozinhaConversor;
	
	public List<CozinhaDTO> listar() {
		return cozinhaConversor.converterListaParaDTO(cozinhaRepository.findAll());
	}
	
	public List<CozinhaDTO> listarPorNome(String nome) {		
		List<Cozinha> cozinhas = cozinhaRepository.findByNome(nome);
		return cozinhaConversor.converterListaParaDTO(cozinhas);
	}
	
	public CozinhaDTO buscarPorId(Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncotradaException(cozinhaId));		
		return cozinhaConversor.converterParaDTO(cozinha);
	}
	
	public CozinhaDTO bucarPrimeiro() {		
		Optional<Cozinha> cozinha = cozinhaRepository.buscarPrimeiro();
		return cozinhaConversor.converterParaDTO(cozinha.get());
	}
	
	@Transactional
	public CozinhaDTO salvar(CozinhaDTO cozinhaDTO) {
		try {
			Cozinha cozinha = cozinhaConversor.converterParaObjeto(cozinhaDTO);
			return cozinhaConversor.converterParaDTO(cozinhaRepository.save(cozinha));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}		
	}
	
	@Transactional
	public CozinhaDTO atualizar(Long cozinhaId, CozinhaDTO cozinhaDTO) {
		try {
			Cozinha cozinhaAtual = cozinhaRepository.findById(cozinhaId).get();	
			cozinhaConversor.copiarParaObjeto(cozinhaDTO, cozinhaAtual);
			return cozinhaConversor.converterParaDTO(cozinhaRepository.save(cozinhaAtual));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public void remover(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			
			/*
			 * Força a execução na base de dados antes do commit no final do método anotado com @Transactional
			 * Não faz o commit, esse é feito ao final do método por causa do @Transactional, o flush apenas executa
			 * as operações que o repository colocou na fila do JPA.
			 */
			cozinhaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncotradaException(cozinhaId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));		
		}
	}
	
}
