package com.algaworks.algafood.repository;

import java.util.List;

import com.algaworks.algafood.entity.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> listar();
	
	List<Cozinha> listarPorNome(String nome);
	
	Cozinha buscar(Long id);
	
	Cozinha salvar(Cozinha cozinha);
	
	void remover(Long id);

}