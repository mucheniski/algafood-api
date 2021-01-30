package com.algaworks.algafood.repository;

import com.algaworks.algafood.entity.FotoProduto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryCustomImpl implements ProdutoRepositoryCustom {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public FotoProduto salvarFotoProduto(FotoProduto fotoProduto) {
        return manager.merge(fotoProduto);
    }
}
