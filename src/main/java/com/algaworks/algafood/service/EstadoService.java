package com.algaworks.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.entity.Estado;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.EntidadeNaoEncotradaException;
import com.algaworks.algafood.repository.EstadoRepository;

@Service
public class EstadoService {
	
	private static final String MSG_ESTADO_EM_USO = "Estado com id %d não pode ser removido, está em uso!";
	
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não exite estado com código %d";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado buscarPorId(Long estadoId)	{
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EntidadeNaoEncotradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)) );
	}
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void remover(Long id) {
		try {
			estadoRepository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncotradaException(String.format(MSG_ESTADO_NAO_ENCONTRADO, id));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
			
		}
	}
	
}
