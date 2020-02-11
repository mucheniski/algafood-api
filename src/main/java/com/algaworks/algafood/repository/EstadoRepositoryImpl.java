package com.algaworks.algafood.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.entity.Estado;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Estado> listar() {
		return entityManager.createQuery("from Estado", Estado.class).getResultList();		
	}
	
	@Override
	public Estado buscar(Long id) {
		return entityManager.find(Estado.class, id);
	}
	
	@Override
	@Transactional
	public Estado salvar(Estado estado) {
		return entityManager.merge(estado);
	}
	
	@Override
	@Transactional
	public void remover(Estado estado) {
		estado = buscar(estado.getId());
		entityManager.remove(estado);
	}

}
