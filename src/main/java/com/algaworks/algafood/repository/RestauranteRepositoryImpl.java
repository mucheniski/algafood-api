package com.algaworks.algafood.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.Restaurante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> findByNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		
		String jpql = "from Restaurante "
					+ " where nome like :nome "
					+ " and taxaFrete between :taxaInicial and :taxaFinal";
		
		return manager.createQuery(jpql, Restaurante.class)
						.setParameter("nome", "%" + nome + "%")
						.setParameter("taxaInicial", taxaInicial)
						.setParameter("taxaFinal", taxaFinal)
						.getResultList();
		
	}
	
}
