package com.algaworks.algafood.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.entity.Restaurante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> findByNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("from Restaurante where 0 = 0 ");
		
		Map<String, Object> parametros = new HashMap<>();
		
		if (StringUtils.hasLength(nome)) {
			jpql.append("and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		
		if (taxaInicial != null) {
			jpql.append("and taxaFrete >= :taxaInicial ");
			parametros.put("taxaInicial", taxaInicial);
		}
		
		if (taxaFinal != null) {
			jpql.append("and taxaFrete <= :taxaFinal ");
			parametros.put("taxaFinal", taxaFinal);
		}
		
		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);		
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
		return query.getResultList();
		
	}
	
}
