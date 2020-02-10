package com.algaworks.algafood.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.entity.Restaurante;

public class RestauranteRepositoryImpl implements RestauranteRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Restaurante> listar() {
		return entityManager.createQuery("from Restaurante", Restaurante.class).getResultList();		
	}
	
	@Override
	public Restaurante buscar(Long id) {
		return entityManager.find(Restaurante.class, id);
	}
	
	@Override
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		return entityManager.merge(restaurante);
	}
	
	@Override
	@Transactional
	public void remover(Restaurante restaurante) {
		restaurante = buscar(restaurante.getId());
		entityManager.remove(restaurante);
	}

}
