package com.algaworks.algafood.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.Produto;
import com.algaworks.algafood.entity.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryCustom {

//	@Query("FROM Restaurante WHERE nome LIKE %:nome% AND cozinha.id = :cozinhaId" )
//	List<Restaurante> consultarPorNome(String nome, @Param("cozinhaId") Long cozinhaId); // O nome não precisa do @Param pois como é o mesmo nome do atributo o spring data já faz o binding

	@Query("FROM Produto WHERE id = :produtoId AND restaurante.id = :restauranteId")
	Optional<Produto> buscarProdutoPorRestaurante(@Param("restauranteId") Long restauranteId, @Param("produtoId") Long produtoId);

	List<Produto> findByRestaurante(Restaurante restaurante);
	
	@Query("from Produto produto where produto.ativo = true and produto.restaurante = :restaurante")
	List<Produto> buscaApenasAtivosPorRestaurante(Restaurante restaurante);
	
}
