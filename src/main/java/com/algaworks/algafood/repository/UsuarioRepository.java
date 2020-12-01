package com.algaworks.algafood.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.entity.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
}
