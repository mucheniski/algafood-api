package com.algaworks.algafood.repository;

import java.util.List;

import com.algaworks.algafood.entity.FormaPagamento;

public interface FormaPagamentoRepository {
	
	List<FormaPagamento> listar();
	
	FormaPagamento buscar(Long id);
	
	FormaPagamento salvar(FormaPagamento cozinha);
	
	void remover(FormaPagamento cozinha);

}