package com.algaworks.algafood.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean // Informa que a interface não deve ser levada em conta para instanciar reposítorio pelo Spring Data JPA
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {
	
	Optional<T> buscarPrimeiro();

}
