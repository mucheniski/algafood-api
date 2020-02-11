package com.algaworks.algafood.repository;

import java.util.List;

import com.algaworks.algafood.entity.Estado;

public interface EstadoRepository {
	
	List<Estado> listar();
	
	Estado buscar(Long id);
	
	Estado salvar(Estado cozinha);
	
	void remover(Estado cozinha);

}