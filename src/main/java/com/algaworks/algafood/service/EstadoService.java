package com.algaworks.algafood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.dto.EstadoConversor;
import com.algaworks.algafood.dto.EstadoDTO;
import com.algaworks.algafood.entity.Estado;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.EstadoNaoEncotradaException;
import com.algaworks.algafood.repository.EstadoRepository;

@Service
public class EstadoService {
	
	private static final String MSG_ESTADO_EM_USO = "Estado com id %d não pode ser removido, está em uso!";
		
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoConversor estadoConversor;
	
	public List<EstadoDTO> listar() {
		return estadoConversor.converterListaParaDTO(estadoRepository.findAll());
	}
	
	public EstadoDTO buscarPorId(Long estadoId)	{
		Estado estado = estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncotradaException(estadoId) );
		return estadoConversor.converterParaDTO(estado);
	}
	
	@Transactional
	public EstadoDTO salvar(EstadoDTO estadoDTO) {
		Estado estado = estadoConversor.converterParaObjeto(estadoDTO);
		return estadoConversor.converterParaDTO(estadoRepository.save(estado));
	}
	
	@Transactional
	public EstadoDTO atualizar(Long estadoId, EstadoDTO estadoDTO) {
		Estado estadoAtual = estadoRepository.findById(estadoId).get();
		estadoConversor.copiarParaObjeto(estadoDTO, estadoAtual);
		return estadoConversor.converterParaDTO(estadoRepository.save(estadoAtual));
	}
	
	@Transactional
	public void remover(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncotradaException(estadoId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, estadoId));
			
		}
	}
	
}
