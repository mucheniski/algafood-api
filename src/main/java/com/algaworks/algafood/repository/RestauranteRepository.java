package com.algaworks.algafood.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.entity.Restaurante;

@ResponseStatus
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {
	
	/*
	 * Vai fazer apenas um select por causa do join, e não selects em todas as 
	 * as cozinhas quando o listar do controller ser chamado, no modo EAGER
	 * 
	 * O fetch no jpql não é feito automáticamente no relacionamento OneToMany, por esse motivo precisa ser colocado sobre o jpql
	 * 
	 */
	@Query("from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
	List<Restaurante> findAllCustom();
	
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	// List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);
	
//	@Query("FROM Restaurante WHERE nome LIKE %:nome% AND cozinha.id = :cozinhaId" )
//	List<Restaurante> consultarPorNome(String nome, @Param("cozinhaId") Long cozinhaId); // O nome não precisa do @Param pois como é o mesmo nome do atributo o spring data já faz o binding

	List<Restaurante> consultarPorNome(String nome, @Param("cozinhaId") Long cozinhaId);
	
}
