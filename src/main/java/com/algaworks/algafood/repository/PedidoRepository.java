package com.algaworks.algafood.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {
	
	/*
	 * join fetch no jpql serve para que apenas uma consulta seja feita retornando todos os relacionamentos de uma só vez, 
	 * ao contrário da consulta sem o fetch que faz vários selects separadamente.
	 * mais detalhes do README do projeto
	 */
	@Query("from Pedido pedido "
		 + "join fetch pedido.usuarioCliente "
		 + "join fetch pedido.restaurante restaurante "
		 + "join fetch restaurante.cozinha ")
	List<Pedido> buscarTodosResumido();
	
	
	Optional<Pedido> findByCodigo(String codigo);

}
