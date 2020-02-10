package com.algaworks.algafood.repository;

import java.util.List;

import com.algaworks.algafood.entity.Restaurante;

public interface RestauranteRepository {

	List<Restaurante> listar();
	
	Restaurante buscar(Long id);
	
	Restaurante salvar(Restaurante restaurante);
	
	void remover(Restaurante restaurante);
	
}
