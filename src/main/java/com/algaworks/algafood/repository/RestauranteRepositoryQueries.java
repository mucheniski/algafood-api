package com.algaworks.algafood.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.entity.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> findByNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

}