package com.algaworks.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.entity.Cidade;
import com.algaworks.algafood.entity.Estado;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.EntidadeNaoEncotradaException;
import com.algaworks.algafood.repository.CidadeRepository;

@Service
public class CidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade com id %d não pode ser removida, está em uso!";
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cidade com código %d";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	public Cidade buscarPorId(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new EntidadeNaoEncotradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)) );
	}
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoService.buscarPorId(estadoId);
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	public void remover(Long id) {
		try {
			cidadeRepository.deleteById(id);	
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncotradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, id));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
			
		}
	}

}
