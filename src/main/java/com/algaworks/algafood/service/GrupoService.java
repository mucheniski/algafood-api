package com.algaworks.algafood.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.dto.GrupoEntradaDTO;
import com.algaworks.algafood.dto.GrupoRetornoDTO;
import com.algaworks.algafood.dto.conversor.GrupoConversor;
import com.algaworks.algafood.entity.Grupo;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.repository.GrupoRepository;

@Service
public class GrupoService {
	
	private static final String MGS_GRUPO_EM_USO = "Grupo com id %d não pode ser removido, está em uso!";
	
	@Autowired
	private GrupoRepository repository;
	
	@Autowired
	private GrupoConversor conversor;
	
	public List<GrupoRetornoDTO> listar() {
		return conversor.converterListaParaDTO(repository.findAll());
	}

	public GrupoRetornoDTO buscarPorId(Long id) {		
		Grupo grupo = buscar(id);
		return conversor.converterParaDTO(grupo);
	}

	@Transactional
	public GrupoRetornoDTO salvar(GrupoEntradaDTO dto) {		
		Grupo grupo = conversor.converterParaObjeto(dto);
		return conversor.converterParaDTO(repository.save(grupo));
	}

	@Transactional
	public GrupoRetornoDTO atualizar(Long id, GrupoEntradaDTO dto) {
		Grupo grupo = buscar(id);
		conversor.copiarParaObjeto(dto, grupo);
		return conversor.converterParaDTO(grupo);
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(id);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MGS_GRUPO_EM_USO, id) );			
		}		
	}	
	
	private Grupo buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}

}
