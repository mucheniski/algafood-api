package com.algaworks.algafood.repository;

import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.entity.FotoProduto;
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

	/*
		Como está sendo feito join com outra instancia é preciso informar
		o select para saber o que precisa ser retornado, no caso só quero
		que retorne os dados de fotoProduto
	 */
	@Query(" select fotoProduto"
		+  " from FotoProduto fotoProduto "
	    +  " join Produto produto "
	    +  " where produto.restaurante.id = :restauranteId "
	    +  " and produto.id = :produtoId ")
	Optional<FotoProduto> buscarFotoPorId(Long restauranteId, Long produtoId);
	
}
