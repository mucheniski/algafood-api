package com.algaworks.algafood.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.entity.Restaurante;

import lombok.AllArgsConstructor;

@AllArgsConstructor // O lombok já cria um construtor recebendo a variável nome
public class RestauranteComNomeSemelhanteSpec implements Specification<Restaurante> {

	private static final long serialVersionUID = 1L;
	
	private String nome;
		
	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
	}
	
}
