package com.algaworks.algafood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.entity.Restaurante;

@ResponseStatus
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	
}
