package com.algaworks.algafood.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.entity.Restaurante;
import com.algaworks.algafood.repository.spec.RestauranteSpecs;

@Repository
public class RestauranteRepositoryCustomImpl implements RestauranteRepositoryCustom {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	@Lazy // Só carrega quando chamado, para evitar erro de dependência circular
	private RestauranteRepository restauranteRepository;
	
	@Override
	public List<Restaurante> findByNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {		

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> query = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = query.from(Restaurante.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if (taxaInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial));
		}
		
		if (taxaFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));
		}
		
		query.where(predicates.toArray(new Predicate[0])); // TODO: ver como melhorar essa conversão
		
		return manager.createQuery(query).getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
	}
	
}
