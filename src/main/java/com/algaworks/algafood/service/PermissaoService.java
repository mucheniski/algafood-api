package com.algaworks.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.dto.conversor.PermissaoConversor;
import com.algaworks.algafood.entity.Permissao;
import com.algaworks.algafood.repository.PermissaoRepository;

import com.algaworks.algafood.exception.PermissaoNaoEncontradaException;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository repository;

	@Autowired
	private PermissaoConversor conversor;
	
	public Permissao buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradaException(id));
	}

}
