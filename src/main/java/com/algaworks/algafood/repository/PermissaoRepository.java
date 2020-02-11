package com.algaworks.algafood.repository;

import java.util.List;

import com.algaworks.algafood.entity.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> listar();
	
	Permissao buscar(Long id);
	
	Permissao salvar(Permissao cozinha);
	
	void remover(Permissao cozinha);

}