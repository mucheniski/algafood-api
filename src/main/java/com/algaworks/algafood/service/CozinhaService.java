package com.algaworks.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.EntidadeNaoEncotradaException;
import com.algaworks.algafood.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void remover(Long id) {
		try {
			cozinhaRepository.deleteById(id);	
		} catch (EmptyResultDataAccessException e) {
			/*
			 * Agora, caso seja necessário eu consigo passar um http status para o construtor 
			 * customizado da classe. Se não passada nada pega a padrão.
			 * */
			throw new EntidadeNaoEncotradaException(HttpStatus.BAD_REQUEST, String.format("Não exite cozinha com código %d", id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha id %d não pode ser removida, está em uso!", id));
		}
	}
	
}
