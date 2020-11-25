package com.algaworks.algafood.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.dto.FormaPagamentoConversor;
import com.algaworks.algafood.dto.FormaPagamentoDTO;
import com.algaworks.algafood.entity.FormaPagamento;
import com.algaworks.algafood.exception.EntidadeEmUsoException;
import com.algaworks.algafood.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.exception.NegocioException;
import com.algaworks.algafood.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento com o id %d não pode ser removida, está em uso";
	
	@Autowired
	private FormaPagamentoRepository repository;
	
	@Autowired
	private FormaPagamentoConversor conversor;
	
	public List<FormaPagamentoDTO> listar() {		
		return conversor.converterListaParaDTO(repository.findAll());		
	}
		
	public FormaPagamentoDTO buscarPorId(Long id) {
		FormaPagamento formaPagamento = repository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
		return conversor.converterParaDTO(formaPagamento);
	}

	@Transactional
	public FormaPagamentoDTO salvar(FormaPagamentoDTO dto) {
		try {
			FormaPagamento formaPagamento = conversor.converterParaObjeto(dto);
			return conversor.converterParaDTO(repository.save(formaPagamento));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Transactional
	public FormaPagamentoDTO atualizar(Long id, @Valid FormaPagamentoDTO dto) {
		try {
			FormaPagamento formaPagamentoAtual = repository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
			conversor.copiarParaObjeto(dto, formaPagamentoAtual);
			return conversor.converterParaDTO(repository.save(formaPagamentoAtual));
		} catch (Exception e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Transactional
	public void remover(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id) );			
		}
		
	}	

}
