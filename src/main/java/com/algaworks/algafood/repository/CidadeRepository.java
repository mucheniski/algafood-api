package com.algaworks.algafood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}