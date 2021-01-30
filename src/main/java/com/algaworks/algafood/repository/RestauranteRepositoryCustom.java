package com.algaworks.algafood.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.entity.Restaurante;

public interface RestauranteRepositoryCustom {

	List<Restaurante> findByNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}