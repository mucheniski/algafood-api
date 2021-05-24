package com.algaworks.algafood.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.dto.CozinhaDTO;
import com.algaworks.algafood.dto.conversor.CozinhaConversor;
import com.algaworks.algafood.entity.Cozinha;
import com.algaworks.algafood.exception.CozinhaNaoEncotradaException;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.NegocioException;
import com.algaworks.algafood.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha id %d não pode ser removida, está em uso!";
	// TODO: Refatorar os nomes para repository e conversor
	@Autowired
	private CozinhaRepository respository;
	
	@Autowired
	private CozinhaConversor conversor;

	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	public PagedModel<CozinhaDTO> listar(Pageable pageable) {
		Page<Cozinha> cozinhasPaginada = respository.findAll(pageable);		
		PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPaginada, conversor);
		return cozinhasPagedModel;
	}

	public List<CozinhaDTO> listarSemPaginacao() {
		List<Cozinha> cozinhas = respository.findAll();
		List<CozinhaDTO> cozinhasDTO = conversor.toCollectionModel(cozinhas);
		return cozinhasDTO;
	}
	
	public List<CozinhaDTO> listarPorNome(String nome) {		
		List<Cozinha> cozinhas = respository.findByNome(nome);
		return conversor.toCollectionModel(cozinhas);
	}
	
	public CozinhaDTO buscarPorId(Long id) {
		Cozinha cozinha = respository.findById(id).orElseThrow(() -> new CozinhaNaoEncotradaException(id));		
		return conversor.toModel(cozinha);
	}
	
	public CozinhaDTO bucarPrimeiro() {		
		Optional<Cozinha> cozinha = respository.buscarPrimeiro();
		return conversor.toModel(cozinha.get());
	}
	
	@Transactional
	public CozinhaDTO salvar(CozinhaDTO dto) {
		try {
			Cozinha cozinha = conversor.converterParaObjeto(dto);
			return conversor.toModel(respository.save(cozinha));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}		
	}
	
	@Transactional
	public CozinhaDTO atualizar(Long id, CozinhaDTO dto) {
		try {
			Cozinha cozinhaAtual = respository.findById(id).get();	
			conversor.copiarParaObjeto(dto, cozinhaAtual);
			return conversor.toModel(respository.save(cozinhaAtual));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			respository.deleteById(id);
			
			/*
			 * Força a execução na base de dados antes do commit no final do método anotado com @Transactional
			 * Não faz o commit, esse é feito ao final do método por causa do @Transactional, o flush apenas executa
			 * as operações que o repository colocou na fila do JPA.
			 */
			respository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncotradaException(id);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));		
		}
	}
	
}
