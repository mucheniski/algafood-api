package com.algaworks.algafood.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
	
	List<Cozinha> findByNome(String nome);

}