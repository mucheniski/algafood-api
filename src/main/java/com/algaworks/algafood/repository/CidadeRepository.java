package com.algaworks.algafood.repository;

import java.util.List;

import com.algaworks.algafood.entity.Cidade;

public interface CidadeRepository {
	
	List<Cidade> listar();
	
	Cidade buscar(Long id);
	
	Cidade salvar(Cidade cozinha);
	
	void remover(Cidade cozinha);

}