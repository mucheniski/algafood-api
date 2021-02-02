package com.algaworks.algafood.repository;

import com.algaworks.algafood.entity.FotoProduto;

public interface ProdutoRepositoryCustom {

    FotoProduto salvarFotoProduto(FotoProduto fotoProduto);

    void apagaFotoProduto(FotoProduto fotoProduto);

}
