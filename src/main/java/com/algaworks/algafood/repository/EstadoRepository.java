package com.algaworks.algafood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}