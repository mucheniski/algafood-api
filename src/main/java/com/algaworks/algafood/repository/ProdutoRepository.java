package com.algaworks.algafood.repository;

import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
		
//	@Query("FROM Restaurante WHERE nome LIKE %:nome% AND cozinha.id = :cozinhaId" )
//	List<Restaurante> consultarPorNome(String nome, @Param("cozinhaId") Long cozinhaId); // O nome não precisa do @Param pois como é o mesmo nome do atributo o spring data já faz o binding

	@Query("FROM Produto WHERE id = :produtoId AND rastaurante.id = :restauranteId")
	Optional<Produto> buscarProdutoPorRestaurante(@Param("restauranteId") Long restauranteId, @Param("produtoId") Long produtoId);	
	
}
