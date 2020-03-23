package com.algaworks.algafood.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.entity.Restaurante;

@ResponseStatus
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	// List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);
	
//	@Query("FROM Restaurante WHERE nome LIKE %:nome% AND cozinha.id = :cozinhaId" )
//	List<Restaurante> consultarPorNome(String nome, @Param("cozinhaId") Long cozinhaId); // O nome não precisa do @Param pois como é o mesmo nome do atributo o spring data já faz o binding

	List<Restaurante> consultarPorNome(String nome, @Param("cozinhaId") Long cozinhaId);
	
}
