package com.algaworks.algafood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.entity.Permissao;
import com.algaworks.algafood.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository repository;
	
	public Permissao buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new PermissaoNaoEncontradaException(id));
	}

}
