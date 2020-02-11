package com.algaworks.algafood.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.entity.Permissao;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Permissao> listar() {
		return entityManager.createQuery("from Permissao", Permissao.class).getResultList();		
	}
	
	@Override
	public Permissao buscar(Long id) {
		return entityManager.find(Permissao.class, id);
	}
	
	@Override
	@Transactional
	public Permissao salvar(Permissao permissao) {
		return entityManager.merge(permissao);
	}
	
	@Override
	@Transactional
	public void remover(Permissao permissao) {
		permissao = buscar(permissao.getId());
		entityManager.remove(permissao);
	}

}
