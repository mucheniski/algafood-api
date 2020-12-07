package com.algaworks.algafood.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.dto.PermissaoDTO;
import com.algaworks.algafood.dto.conversor.GrupoConversor;
import com.algaworks.algafood.dto.conversor.PermissaoConversor;
import com.algaworks.algafood.dto.entrada.GrupoEntradaDTO;
import com.algaworks.algafood.dto.retorno.GrupoRetornoDTO;
import com.algaworks.algafood.entity.Grupo;
import com.algaworks.algafood.entity.Permissao;
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
	
	@Autowired
	private PermissaoConversor permissaoConversor;
	
	@Autowired
	private PermissaoService permissaoService;
	
	public List<GrupoRetornoDTO> listar() {
		return conversor.converterListaParaDTO(repository.findAll());
	}

	public GrupoRetornoDTO buscarDtoPorId(Long id) {		
		Grupo grupo = buscarPorId(id);
		return conversor.converterParaDTO(grupo);
	}

	@Transactional
	public GrupoRetornoDTO salvar(GrupoEntradaDTO dto) {		
		Grupo grupo = conversor.converterParaObjeto(dto);
		return conversor.converterParaDTO(repository.save(grupo));
	}

	@Transactional
	public GrupoRetornoDTO atualizar(Long id, GrupoEntradaDTO dto) {
		Grupo grupo = buscarPorId(id);
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
	
	public Grupo buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}

	public List<PermissaoDTO> listaPermissoesPorGrupo(Long grupoId) {
		List<PermissaoDTO> produtos = new ArrayList<>();
		Grupo grupo = buscarPorId(grupoId); 
		if(!grupo.getPermissoes().isEmpty()) {
			produtos = permissaoConversor.converterListaParaDTO(grupo.getPermissoes());
		}
		return produtos;	
	}

	@Transactional
	public void desvincularPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarPorId(grupoId);
		Permissao permissao = permissaoService.buscarPorId(permissaoId);
		grupo.desvincularPermissao(permissao);
	}

	// TODO: não está com idempotencia, verificar
	@Transactional
	public void vincularPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarPorId(grupoId);
		Permissao permissao = permissaoService.buscarPorId(permissaoId);		
		grupo.vincularPermissao(permissao);		
	}

}
