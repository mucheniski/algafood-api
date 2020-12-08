package com.algaworks.algafood.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.dto.GrupoDTO;
import com.algaworks.algafood.dto.PermissaoDTO;
import com.algaworks.algafood.dto.conversor.GrupoConversor;
import com.algaworks.algafood.dto.conversor.PermissaoConversor;
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
	
	public List<GrupoDTO> listar() {
		return conversor.converterListaParaDTO(repository.findAll());
	}
	
	public Grupo buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}

	public GrupoDTO buscarDtoPorId(Long id) {		
		Grupo grupo = buscarPorId(id);
		return conversor.converterParaDTO(grupo);
	}

	@Transactional
	public GrupoDTO salvar(GrupoDTO dto) {		
		Grupo grupo = conversor.converterParaObjeto(dto);
		return conversor.converterParaDTO(repository.save(grupo));
	}

	@Transactional
	public GrupoDTO atualizar(Long id, GrupoDTO dto) {
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

	public List<PermissaoDTO> listaPermissoesPorGrupo(Long grupoId) {
		List<PermissaoDTO> permissoes = new ArrayList<>();
		Grupo grupo = buscarPorId(grupoId); 
		if(!grupo.getPermissoes().isEmpty()) {
			permissoes = permissaoConversor.converterListaParaDTO(grupo.getPermissoes());
		}
		return permissoes;	
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

	public List<GrupoDTO> converterListaParaDTO(List<Grupo> grupos) {		
		return conversor.converterListaParaDTO(grupos);
	}

}
