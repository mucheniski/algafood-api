package com.algaworks.algafood.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.entity.Pedido;
import com.algaworks.algafood.repository.filtro.PedidoFiltro;

public class PedidoSpecs {
	
	public static Specification<Pedido> comFiltro(PedidoFiltro filtro) {		
	
		return (root, query, builder) -> {
			
			/*
			 * para resolver o problema de N+1 ou seja, para cada entidade aninhada ou relacionada ser feito um select
			 * direferente, usamos o fetch do root, assim é feito apenas um select com todas as informações pelo spring
			 * data JPA, é o mesmo fetch feito no jpql mas agora com criteria.
			 * Observando o console a pós o get pode ser verificado que é criada apenas um select.
			 */
			root.fetch("restaurante").fetch("cozinha");
			root.fetch("usuarioCliente");
			
			List<Predicate> predicates = new ArrayList<>();
			
			if (filtro.getUsuarioClienteId() != null) {
				predicates.add(builder.equal(root.get("usuarioCliente"), filtro.getUsuarioClienteId()));
			}
			
			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
			}
			
			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			}
			
			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));			
		};
		
	}

}
