package com.algaworks.algafood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.dto.EstadoDTO;
import com.algaworks.algafood.dto.conversor.EstadoConversor;
import com.algaworks.algafood.entity.Estado;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.EstadoNaoEncotradaException;
import com.algaworks.algafood.repository.EstadoRepository;

@Service
public class EstadoService {
	
	private static final String MSG_ESTADO_EM_USO = "Estado com id %d não pode ser removido, está em uso!";
	@Autowired
	private EstadoRepository reposiroty;
	
	@Autowired
	private EstadoConversor conversor;
	
	public CollectionModel<EstadoDTO> listar() {
		return conversor.toCollectionModel(reposiroty.findAll());
	}
	
	public EstadoDTO buscarDtoPorId(Long id)	{
		Estado estado = reposiroty.findById(id).orElseThrow(() -> new EstadoNaoEncotradaException(id) );
		return conversor.toModel(estado);
	}
	
	@Transactional
	public EstadoDTO salvar(EstadoDTO dto) {
		Estado estado = conversor.converterParaObjeto(dto);
		return conversor.toModel(reposiroty.save(estado));
	}
	
	@Transactional
	public EstadoDTO atualizar(Long id, EstadoDTO dto) {
		Estado estadoAtual = reposiroty.findById(id).get();
		conversor.copiarParaObjeto(dto, estadoAtual);
		return conversor.toModel(reposiroty.save(estadoAtual));
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			reposiroty.deleteById(id);
			reposiroty.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncotradaException(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
			
		}
	}
	
}
