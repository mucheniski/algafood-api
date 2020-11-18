package com.algaworks.algafood.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {
		
	private EntityManager manager;	
	
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.manager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {		
		String jpql = "From " + getDomainClass().getName() ;
		T entity = manager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult(); // Gera uma consulta sql limitando o resultado em apenas uma linha
												   					
		return Optional.ofNullable(entity); // Pode ser um Optional null ou com a entitade retornada
	}
	
}
