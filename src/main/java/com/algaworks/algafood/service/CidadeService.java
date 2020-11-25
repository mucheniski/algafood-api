package com.algaworks.algafood.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.dto.CidadeConversor;
import com.algaworks.algafood.dto.CidadeDTO;
import com.algaworks.algafood.entity.Cidade;
import com.algaworks.algafood.entity.Estado;
import com.algaworks.algafood.exception.CidadeNaoEncotradaException;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.EstadoNaoEncotradaException;
import com.algaworks.algafood.exception.NegocioException;
import com.algaworks.algafood.repository.CidadeRepository;
import com.algaworks.algafood.repository.EstadoRepository;

@Service
public class CidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade com id %d não pode ser removida, está em uso!";
	// TODO: Refatorar os nomes para repository e conversor
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeConversor cidadeConversor;
	
	public List<CidadeDTO> listar() {
		return cidadeConversor.converterListaParaDTO(cidadeRepository.findAll());
	}
	
	public CidadeDTO buscarPorId(Long cidadeId) {
		Cidade cidade = cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncotradaException(cidadeId));		
		return cidadeConversor.converterParaDTO(cidade);
	}
	
	@Transactional
	public CidadeDTO salvar(CidadeDTO cidadeDTO) {		
		try {
			Cidade cidade = cidadeConversor.converterParaObjeto(cidadeDTO);
			Optional<Estado> estado = estadoRepository.findById(cidadeDTO.getEstado().getId());
			cidade.setEstado(estado.get());
			return cidadeConversor.converterParaDTO(cidadeRepository.save(cidade));
		} catch (EstadoNaoEncotradaException e) {
			throw new NegocioException(e.getMessage());
		}		
	}
	
	@Transactional
	public CidadeDTO atualizar(Long cidadeId, CidadeDTO cidadeDTO) {		
		try {
			Cidade cidadeAtual = cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncotradaException(cidadeId));
			
			// Se quiser alterar o estado, basta passar o novo estado.id no JSON de CidadeDTO
			if (cidadeDTO.getEstado() != null) {
				Long estadoId = cidadeDTO.getEstado().getId();				
				Estado novoEstado = estadoRepository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncotradaException(estadoId));
				cidadeAtual.setEstado(novoEstado);
			}
			
			cidadeConversor.copiarParaObjeto(cidadeDTO, cidadeAtual);
			return cidadeConversor.converterParaDTO(cidadeRepository.save(cidadeAtual));
		} catch (EstadoNaoEncotradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public void remover(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			cidadeRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncotradaException(cidadeId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId) );			
		}
	}

}
